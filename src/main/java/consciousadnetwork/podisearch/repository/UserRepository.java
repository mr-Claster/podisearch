package consciousadnetwork.podisearch.repository;

import consciousadnetwork.podisearch.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT DISTINCT u FROM User u "
            + "LEFT JOIN FETCH u.roles "
            + "WHERE u.email =:email")
    Optional<User> findByEmailFetchRoles(String email);
}
