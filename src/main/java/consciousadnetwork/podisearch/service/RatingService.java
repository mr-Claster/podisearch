package consciousadnetwork.podisearch.service;

import consciousadnetwork.podisearch.model.Mark;
import consciousadnetwork.podisearch.model.User;

public interface RatingService {

    Mark markPodcast(Byte mark, String podcastName, User user);
}
