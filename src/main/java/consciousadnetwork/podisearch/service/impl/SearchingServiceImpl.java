package consciousadnetwork.podisearch.service.impl;

import consciousadnetwork.podisearch.dto.response.CollectionSearchResponseDto;
import consciousadnetwork.podisearch.model.SearchType;
import consciousadnetwork.podisearch.repository.CollectionRepository;
import consciousadnetwork.podisearch.service.SearchingService;
import consciousadnetwork.podisearch.service.strategy.ByWordsInOrderSearchStrategy;
import consciousadnetwork.podisearch.service.strategy.ByWordsSearchStrategy;
import consciousadnetwork.podisearch.service.strategy.SearchStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class SearchingServiceImpl implements SearchingService {

    private final CollectionRepository collectionRepository;

    private final Map<SearchType, SearchStrategy> strategy;

    public SearchingServiceImpl(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;

        strategy = new HashMap<>();
        strategy.put(SearchType.BY_WORDS, new ByWordsSearchStrategy());
        strategy.put(SearchType.BY_WORDS_IN_ORDER, new ByWordsInOrderSearchStrategy());
    }

    @Override
    public List<CollectionSearchResponseDto> search(String text) {
        if (text.trim().isEmpty()) {
            throw new IllegalArgumentException("Search text cannot be null or empty");
        }
        return strategy
                .get(determineSearchType(text))
                .search(text, collectionRepository);
    }

    private SearchType determineSearchType(String text) {
        return isQuote(text) ?
                SearchType.BY_WORDS_IN_ORDER : SearchType.BY_WORDS;
    }

    private boolean isQuote(String text) {
        return text.startsWith("\"") && text.endsWith("\"");
    }
}
