package consciousadnetwork.podisearch.service.strategy;

import consciousadnetwork.podisearch.dto.response.CollectionSearchResponseDto;
import consciousadnetwork.podisearch.repository.CollectionRepository;
import java.util.Arrays;
import java.util.List;

public class ByWordsSearchStrategy implements SearchStrategy {

    @Override
    public List<CollectionSearchResponseDto> search(String text, CollectionRepository collectionRepository) {
        List<String> words = splitWords(text);
        return collectionRepository.findPodcastByWords(words);
    }

    private List<String> splitWords(String text) {
        return Arrays
                .stream(text.toLowerCase().split("\\s+"))
                .toList();
    }
}
