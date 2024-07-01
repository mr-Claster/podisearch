package consciousadnetwork.podisearch.service;

import consciousadnetwork.podisearch.dto.response.CollectionSearchResponseDto;
import consciousadnetwork.podisearch.model.Collection;
import consciousadnetwork.podisearch.model.Podcast;
import java.util.List;

public interface CollectionService {

    Collection create(Collection collection);

    Collection addPodcastByName(Podcast podcast, String collectionName);

    Collection addPodcastById(Podcast podcast, String collectionId);

    List<CollectionSearchResponseDto> findByWords(String text);
}
