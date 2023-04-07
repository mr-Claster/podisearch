package consciousadnetwork.podisearch.service;

import consciousadnetwork.podisearch.model.User;

public interface UserService {
    User getByUsername(String username);

    User save(User user);
}
