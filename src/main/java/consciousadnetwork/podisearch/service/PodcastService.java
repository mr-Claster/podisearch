package consciousadnetwork.podisearch.service;

import consciousadnetwork.podisearch.dto.response.PodcastSearchResponseDto;
import consciousadnetwork.podisearch.model.Podcast;
import java.util.List;

public interface PodcastService {

    Podcast save(Podcast podcast);

    List<PodcastSearchResponseDto> findByText(String text);

    Podcast findPodcastByTitle(String podcastTitle);

    Podcast findPodcastById(String podcastId);
}
