package consciousadnetwork.podisearch.service.strategy;

import consciousadnetwork.podisearch.dto.response.CollectionSearchResponseDto;
import consciousadnetwork.podisearch.repository.CollectionRepository;
import java.util.List;

public interface SearchStrategy {
    List<CollectionSearchResponseDto> search(String text, CollectionRepository collectionRepository);
}
