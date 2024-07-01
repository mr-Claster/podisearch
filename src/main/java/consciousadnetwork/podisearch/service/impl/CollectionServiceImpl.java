package consciousadnetwork.podisearch.service.impl;

import consciousadnetwork.podisearch.dto.response.CollectionSearchResponseDto;
import consciousadnetwork.podisearch.model.Collection;
import consciousadnetwork.podisearch.model.Podcast;
import consciousadnetwork.podisearch.repository.CollectionRepository;
import consciousadnetwork.podisearch.service.CollectionService;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository collectionRepository;

    public CollectionServiceImpl(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    @Override
    public Collection create(Collection collection) {
        return collectionRepository.save(collection);
    }

    @Override
    public Collection addPodcastByName(Podcast podcast, String collectionName) {
        Collection collection = collectionRepository.findByName(collectionName)
                .orElseThrow(() -> new RuntimeException(
                        "Can't find collection by name: " + collectionName));
        collection.getPodcasts().add(podcast);
        return collectionRepository.save(collection);
    }

    @Override
    public Collection addPodcastById(Podcast podcast, String collectionId) {
        Collection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new RuntimeException(
                        "Can't find collection by id: " + collectionId));
        collection.getPodcasts().add(podcast);
        return collectionRepository.save(collection);
    }

    @Override
    public List<CollectionSearchResponseDto> findByWords(String text) {//TODO: create for searching new class
        List<String> words = Arrays.stream(text.toLowerCase().split(" ")).toList();
        return collectionRepository.findPodcastByWordsInOrder(words.size(), words);
    }
}
