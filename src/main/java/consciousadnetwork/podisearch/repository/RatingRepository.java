package consciousadnetwork.podisearch.repository;

import consciousadnetwork.podisearch.model.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Mark, Long> {
}
