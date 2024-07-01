package consciousadnetwork.podisearch.repository;

import consciousadnetwork.podisearch.dto.response.PodcastSearchResponseDto;
import consciousadnetwork.podisearch.model.Podcast;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PodcastRepository extends MongoRepository<Podcast, String> {

    Optional<Podcast> findPodcastByTitle(String title);

    @Aggregation(pipeline = {
            "{ $match: { 'words.word': ?0 } }",
            "{ $group: { _id: '$title',"
                    + " title: {$first: '$title'},"
                    + " words: {$first: '$words'}}}"
    })
    List<PodcastSearchResponseDto> findPodcastsByWords(String word);
}
