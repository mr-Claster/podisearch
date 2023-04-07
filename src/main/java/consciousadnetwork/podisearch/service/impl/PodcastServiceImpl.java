package consciousadnetwork.podisearch.service.impl;

import consciousadnetwork.podisearch.model.Podcast;
import consciousadnetwork.podisearch.repository.PodcastRepository;
import consciousadnetwork.podisearch.service.PodcastService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PodcastServiceImpl implements PodcastService {
    private final PodcastRepository podcastRepository;

    public PodcastServiceImpl(PodcastRepository podcastRepository) {
        this.podcastRepository = podcastRepository;
    }

    @Override
    public Podcast savePodcast(Podcast podcast) {
        return podcastRepository.save(podcast);
    }

    @Override
    public List<Podcast> findByText(String text) {
        return podcastRepository.findPodcastsByWord(text);
    }
}
