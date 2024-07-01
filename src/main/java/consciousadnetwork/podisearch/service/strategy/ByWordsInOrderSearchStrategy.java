package consciousadnetwork.podisearch.service.strategy;

import consciousadnetwork.podisearch.dto.response.CollectionSearchResponseDto;
import consciousadnetwork.podisearch.repository.CollectionRepository;
import java.util.Arrays;
import java.util.List;

public class ByWordsInOrderSearchStrategy implements SearchStrategy {

    @Override
    public List<CollectionSearchResponseDto> search(String text, CollectionRepository collectionRepository) {
        List<String> words = splitWords(text);
        return collectionRepository.findPodcastByWordsInOrder(words.size(), words);
    }

    private List<String> splitWords(String text) {
        return Arrays
                .stream(text.substring(1, text.length() - 1)
                        .toLowerCase()
                        .split("\\s+"))
                .toList();
    }
}
