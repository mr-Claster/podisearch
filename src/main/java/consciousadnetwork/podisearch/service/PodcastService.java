package consciousadnetwork.podisearch.service;

import consciousadnetwork.podisearch.model.Podcast;
import java.util.List;

public interface PodcastService {
    Podcast savePodcast(Podcast podcast);

    List<Podcast> findByText(String text);
}
