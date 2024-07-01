package consciousadnetwork.podisearch.repository;

import consciousadnetwork.podisearch.model.Provider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, Long> {

    Optional<Provider> findByProvider(String provider);
}
