package consciousadnetwork.podisearch.service.impl;

import consciousadnetwork.podisearch.model.Role;
import consciousadnetwork.podisearch.model.User;
import consciousadnetwork.podisearch.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUsername(username);
        return org.springframework.security.core.userdetails.User.withUsername(username)
                .password(user.getPassword())
                .roles(user.getRoles().stream()
                        .map(Role::getRoleName)
                        .toArray(String[]::new))
                .build();
    }
}
