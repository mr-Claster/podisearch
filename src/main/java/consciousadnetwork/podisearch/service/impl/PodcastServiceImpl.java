package consciousadnetwork.podisearch.service.impl;

import consciousadnetwork.podisearch.dto.response.PodcastSearchResponseDto;
import consciousadnetwork.podisearch.model.Podcast;
import consciousadnetwork.podisearch.repository.PodcastRepository;
import consciousadnetwork.podisearch.service.PodcastService;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PodcastServiceImpl implements PodcastService {

    private final PodcastRepository podcastRepository;

    public PodcastServiceImpl(PodcastRepository podcastRepository) {
        this.podcastRepository = podcastRepository;
    }

    @Override
    public Podcast save(Podcast podcast) {
        return podcastRepository.save(podcast);
    }

    @Override
    public List<PodcastSearchResponseDto> findByText(String text) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        return podcastRepository.findPodcastsByWords(text);
    }

    @Override
    public Podcast findPodcastByTitle(String podcastTitle) {
        return podcastRepository.findPodcastByTitle(podcastTitle)
                .orElseThrow(() -> new RuntimeException("can't find podcast by title: "
                        + podcastTitle));
    }

    @Override
    public Podcast findPodcastById(String podcastId) {
        return podcastRepository.findById(podcastId)
                .orElseThrow(() -> new RuntimeException("can't find podcast by id: " + podcastId));
    }
}
