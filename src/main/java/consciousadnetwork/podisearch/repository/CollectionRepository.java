package consciousadnetwork.podisearch.repository;

import consciousadnetwork.podisearch.dto.response.CollectionSearchResponseDto;
import consciousadnetwork.podisearch.model.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CollectionRepository extends MongoRepository<Collection, String> {

    Optional<Collection> findByName(String name);

    @Aggregation(pipeline = {
            "{ $unwind: '$podcasts' }",
            "{ $match: { 'podcasts.words.word': { $in: ?0 } } }",
            "{ $group: { _id: '$_id', "
                    + "'name': { $first: '$name' }, "
                    + "'urlToImg': { $first: '$urlToImg' }, "
                    + "'published': { $first: '$published' }, "
                    + "'viewUrl': { $first: '$viewUrl' }, "
                    + "'podcasts': { $push: '$podcasts' } } }"
    })
    List<CollectionSearchResponseDto> findPodcastByWords(List<String> word);

    @Aggregation(pipeline = {
            "{ $unwind: '$podcasts' }",
            "{ $project: { " +
                    "name: 1, " +
                    "urlToImg: 1, " +
                    "published: 1, " +
                    "viewUrl: 1, " +
                    "podcasts: 1, " +
                    "wordSequences: { $map: { " +
                    "input: { $range: [0, { $size: '$podcasts.words' }] }, " +
                    "as: 'index', " +
                    "in: { $slice: ['$podcasts.words.word', '$$index', ?0] } } } } }",
            "{ $match: { " +
                    "'wordSequences': { $in: [?1] } " +
                    "} }",
            "{ $group: { _id: '$_id', " +
                    "'name': { $first: '$name' }, " +
                    "'urlToImg': { $first: '$urlToImg' }, " +
                    "'published': { $first: '$published' }, " +
                    "'viewUrl': { $first: '$viewUrl' }, " +
                    "'podcasts': { $push: '$podcasts' } } }"
    })
    List<CollectionSearchResponseDto> findPodcastByWordsInOrder(int numberOfWords, List<String> words);

}
