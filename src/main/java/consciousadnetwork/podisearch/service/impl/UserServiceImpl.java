package consciousadnetwork.podisearch.service.impl;

import consciousadnetwork.podisearch.dto.request.SingUpRequestDto;
import consciousadnetwork.podisearch.exception.EmailAlreadyRegisteredException;
import consciousadnetwork.podisearch.exception.UserAlreadyHaveRoleException;
import consciousadnetwork.podisearch.model.Role;
import consciousadnetwork.podisearch.model.User;
import consciousadnetwork.podisearch.repository.UserRepository;
import consciousadnetwork.podisearch.service.ProviderService;
import consciousadnetwork.podisearch.service.RoleService;
import consciousadnetwork.podisearch.service.UserService;
import java.util.List;
import java.util.Optional;
import javax.naming.AuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ProviderService providerService;

    public UserServiceImpl(PasswordEncoder encoder,
                           UserRepository userRepository,
                           RoleService roleService,
                           ProviderService providerService) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.providerService = providerService;
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmailFetchRoles(email)
                .orElseThrow(() ->
                        new RuntimeException("can't find user by email: " + email));
    }

    @Override
    public User save(User user) {
        user.setRoles(List.of(roleService.findByName("USER")));
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getActiveUser() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                return getByEmail(username);
            } else {
                String username = principal.toString();
                return getByEmail(username);
            }
        } else {
            throw new RuntimeException("No active user");
        }
    }

    @Override
    public void addRole(String email, String roleName)
            throws UserAlreadyHaveRoleException {
        User user = getByEmail(email);
        if (user.getRoles()
                .stream()
                .anyMatch(r -> r.getRoleName().equals(roleName))) {
            throw new UserAlreadyHaveRoleException("User already have role: " + roleName);
        }
        Role role = roleService.findByName(roleName);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public User registerNewUser(SingUpRequestDto singUpRequestDto)
            throws EmailAlreadyRegisteredException, AuthenticationException {
        Optional<User> byEmailFetchRole = userRepository
                .findByEmailFetchRoles(singUpRequestDto.getEmail());
        if (byEmailFetchRole.isPresent()) {
            throw new EmailAlreadyRegisteredException("Email is already registered: "
                + singUpRequestDto.getEmail());
        }
        if (! singUpRequestDto.getPassword().equals(singUpRequestDto.getConfirmPassword())) {
            throw new AuthenticationException("Passwords are not the same");
        }
        User registerUser = new User();
        registerUser.setEmail(singUpRequestDto.getEmail());
        registerUser.setPassword(singUpRequestDto.getPassword());
        registerUser.setProvider(providerService.findByName("LOCAL"));
        return save(registerUser);
    }
}
