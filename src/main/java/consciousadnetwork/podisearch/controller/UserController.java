package consciousadnetwork.podisearch.controller;

import consciousadnetwork.podisearch.exception.UserAlreadyHaveRoleException;
import consciousadnetwork.podisearch.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/role/add")
    void addRoleToUser(@RequestParam String email,
                       @RequestParam String roleName)
            throws UserAlreadyHaveRoleException {
        userService.addRole(email, roleName);
    }
}
