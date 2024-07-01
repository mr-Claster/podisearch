package consciousadnetwork.podisearch.security;

import consciousadnetwork.podisearch.model.User;
import consciousadnetwork.podisearch.repository.UserRepository;
import consciousadnetwork.podisearch.service.ProviderService;
import consciousadnetwork.podisearch.service.RoleService;
import java.util.List;
import java.util.Optional;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final ProviderService providerService;
    private final RoleService roleService;

    public CustomOAuth2UserService(UserRepository userRepository,
                                   ProviderService providerService,
                                   RoleService roleService) {
        this.userRepository = userRepository;
        this.providerService = providerService;
        this.roleService = roleService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oauth2UserRequest)
            throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(oauth2UserRequest);
        try {
            return processOAuth2User(oauth2User);
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2User oauth2User) {
        GoogleOAuth2UserInfo oauth2UserInfo = new GoogleOAuth2UserInfo(oauth2User.getAttributes());
        if (!StringUtils.hasText(oauth2UserInfo.getEmail())) {
            throw new RuntimeException("Email not found from OAuth2 provider");
        }
        Optional<User> userOptional = userRepository
                .findByEmailFetchRoles(oauth2UserInfo.getEmail());
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            user = updateExistingUser(user, oauth2UserInfo);
        } else {
            user = registerNewUser(oauth2UserInfo);
        }
        return UserPrincipal.create(user, oauth2User.getAttributes());
    }

    private User registerNewUser(GoogleOAuth2UserInfo oauth2UserInfo) {
        User user = new User();
        user.setProvider(providerService.findByName("GOOGLE"));
        user.setRoles(List.of(roleService.findByName("USER")));
        user.setFirstName(oauth2UserInfo.getFirstName());
        user.setLastName(oauth2UserInfo.getLastName());
        user.setEmail(oauth2UserInfo.getEmail());
        user.setProfileImage(oauth2UserInfo.getImageUrl());
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, GoogleOAuth2UserInfo oauth2UserInfo) {
        existingUser.setFirstName(oauth2UserInfo.getFirstName());
        existingUser.setLastName(oauth2UserInfo.getLastName());
        existingUser.setProfileImage(oauth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }
}
