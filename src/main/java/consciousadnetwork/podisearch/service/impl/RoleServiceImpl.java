package consciousadnetwork.podisearch.service.impl;

import consciousadnetwork.podisearch.model.Role;
import consciousadnetwork.podisearch.repository.RoleRepository;
import consciousadnetwork.podisearch.service.RoleService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByRoleName(name)
                .orElseThrow(() -> new RuntimeException("can't find role by name:" + name));
    }
}
