package consciousadnetwork.podisearch.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import consciousadnetwork.podisearch.dto.response.CollectionSearchResponseDto;
import consciousadnetwork.podisearch.model.Collection;
import consciousadnetwork.podisearch.model.Podcast;
import consciousadnetwork.podisearch.repository.CollectionRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.util.Optional;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CollectionServiceImplTest {
    @Mock
    private CollectionRepository collectionRepository;

    private CollectionServiceImpl collectionService;

    @BeforeEach
    void setUp() {
        collectionService = new CollectionServiceImpl(collectionRepository);
    }

    @Test
    void testCreateCollection() {
        Collection collection = new Collection();
        when(collectionRepository.save(collection)).thenReturn(collection);

        Collection result = collectionService.create(collection);
        assertEquals(collection, result);
    }

    @Test
    void testAddPodcastToCollectionByName() {
        String collectionName = "testCollection";
        Collection collection = new Collection();
        Podcast podcast = new Podcast();

        when(collectionRepository.findByName(collectionName)).thenReturn(Optional.of(collection));
        when(collectionRepository.save(any(Collection.class))).thenReturn(collection);

        Collection updatedCollection = collectionService.addPodcastByName(podcast, collectionName);

        assertTrue(updatedCollection.getPodcasts().contains(podcast));
        verify(collectionRepository, times(1)).save(collection);
    }

    @Test
    void testAddPodcastToCollectionById() {
        String collectionId = "123";
        Collection collection = new Collection();
        Podcast podcast = new Podcast();

        when(collectionRepository.findById(collectionId)).thenReturn(Optional.of(collection));
        when(collectionRepository.save(any(Collection.class))).thenReturn(collection);

        Collection updatedCollection = collectionService.addPodcastById(podcast, collectionId);

        assertTrue(updatedCollection.getPodcasts().contains(podcast));
        verify(collectionRepository, times(1)).save(collection);
    }

    @Test
    void testFindByWords() {
        String searchText = "word1 word2";
        List<CollectionSearchResponseDto> mockResponse = new ArrayList<>();
        when(collectionRepository.findPodcastByWords(anyList())).thenReturn(mockResponse);

        List<CollectionSearchResponseDto> result = collectionService.findByWords(searchText);

        assertEquals(mockResponse, result);
        verify(collectionRepository, times(1)).findPodcastByWords(Arrays.asList("word1", "word2"));
    }
}
