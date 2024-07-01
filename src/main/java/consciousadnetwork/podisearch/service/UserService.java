package consciousadnetwork.podisearch.service;

import consciousadnetwork.podisearch.dto.request.SingUpRequestDto;
import consciousadnetwork.podisearch.exception.EmailAlreadyRegisteredException;
import consciousadnetwork.podisearch.exception.UserAlreadyHaveRoleException;
import consciousadnetwork.podisearch.model.Role;
import consciousadnetwork.podisearch.model.User;
import javax.naming.AuthenticationException;

public interface UserService {

    User getByEmail(String email);

    User save(User user);

    User getActiveUser();

    void addRole(String email, String roleName) throws UserAlreadyHaveRoleException;

    User registerNewUser(SingUpRequestDto singUpRequestDto)
            throws EmailAlreadyRegisteredException, AuthenticationException;
}
