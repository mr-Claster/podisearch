package consciousadnetwork.podisearch.service.impl;

import consciousadnetwork.podisearch.model.Podcast;
import consciousadnetwork.podisearch.model.Mark;
import consciousadnetwork.podisearch.model.User;
import consciousadnetwork.podisearch.repository.RatingRepository;
import consciousadnetwork.podisearch.service.PodcastService;
import consciousadnetwork.podisearch.service.RatingService;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final PodcastService podcastService;

    public RatingServiceImpl(RatingRepository ratingRepository,
                             PodcastService podcastService) {
        this.ratingRepository = ratingRepository;
        this.podcastService = podcastService;
    }

    @Override
    public Mark markPodcast(Byte mark, String podcastId, User user) {
        Mark newMark = new Mark();
        newMark.setPodcastId(podcastId);
        newMark.getUsers().add(user);
        podcastService.save(countNewRatingForPodcast(mark, podcastId));
        ratingRepository.save(newMark);
        return newMark;
    }

    public Podcast countNewRatingForPodcast(byte mark, String podcastId) {
        Podcast podcast = podcastService.findPodcastById(podcastId);
        Long numberOfMarks = podcast.getNumberOfMarks();
        Double averageRating = podcast.getAverageRating();
        podcast.setAverageRating((numberOfMarks * averageRating + mark) / ++numberOfMarks);
        podcast.setNumberOfMarks(numberOfMarks);
        return podcast;
    }
}
