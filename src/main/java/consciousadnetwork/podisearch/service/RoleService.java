package consciousadnetwork.podisearch.service;

import consciousadnetwork.podisearch.model.Role;

public interface RoleService {

    Role save(Role role);

    Role findByName(String name);
}
