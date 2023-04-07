package consciousadnetwork.podisearch.repository;

import consciousadnetwork.podisearch.model.Podcast;
import java.util.List;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PodcastRepository extends MongoRepository<Podcast, String> {
    @Aggregation(pipeline = {
            "{ $match: { 'words.word': ?0 } }",
            "{ $unwind: '$words' }",
            "{ $match: { 'words.word': ?0 } }",
            "{ $group: { _id: '$_id', title: { $first: '$title' },"
                    + " words: { $push: '$words' },"
                    + " urlToImg: { $first: '$urlToImg' },"
                    + " published: { $first: '$published' } } }"
    })
    List<Podcast> findPodcastsByWord(String word);
}
