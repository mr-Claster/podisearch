package consciousadnetwork.podisearch.controller;

import consciousadnetwork.podisearch.dto.request.LoginRequestDto;
import consciousadnetwork.podisearch.dto.request.SingUpRequestDto;
import consciousadnetwork.podisearch.dto.response.UserResponseDto;
import consciousadnetwork.podisearch.exception.EmailAlreadyRegisteredException;
import consciousadnetwork.podisearch.mapper.ResponseDtoMapper;
import consciousadnetwork.podisearch.model.User;
import consciousadnetwork.podisearch.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final ResponseDtoMapper<UserResponseDto, User> userResponseMapper;

    public AuthController(AuthenticationManager authenticationManager,
                          UserService userService,
                          ResponseDtoMapper<UserResponseDto, User> userResponseMapper) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userResponseMapper = userResponseMapper;
    }

    @PostMapping("/singIn")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginRequestDto loginDto) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("Authentication successful");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @PostMapping("/singUp")
    public UserResponseDto registerUser(@RequestBody SingUpRequestDto singUpRequestDto)
            throws EmailAlreadyRegisteredException, javax.naming.AuthenticationException {
        return userResponseMapper.mapToDto(userService.registerNewUser(singUpRequestDto));
    }
}
