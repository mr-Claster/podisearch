package consciousadnetwork.podisearch.service;

import consciousadnetwork.podisearch.dto.response.CollectionSearchResponseDto;
import java.util.List;

public interface SearchingService {

    List<CollectionSearchResponseDto> search(String text);
}
