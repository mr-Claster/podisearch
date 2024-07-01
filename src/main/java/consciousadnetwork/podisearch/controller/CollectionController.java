package consciousadnetwork.podisearch.controller;

import consciousadnetwork.podisearch.dto.request.CollectionRequestDto;
import consciousadnetwork.podisearch.dto.request.PodcastRequestDto;
import consciousadnetwork.podisearch.dto.response.CollectionResponseDto;
import consciousadnetwork.podisearch.dto.response.CollectionSearchResponseDto;
import consciousadnetwork.podisearch.mapper.RequestDtoMapper;
import consciousadnetwork.podisearch.mapper.ResponseDtoMapper;
import consciousadnetwork.podisearch.model.Collection;
import consciousadnetwork.podisearch.model.Podcast;
import consciousadnetwork.podisearch.service.CollectionService;
import consciousadnetwork.podisearch.service.PodcastService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collection")
public class CollectionController {

    private final RequestDtoMapper<PodcastRequestDto, Podcast> podcastRequestDtoMapper;
    private final ResponseDtoMapper<CollectionResponseDto, Collection> collectionResponseDtoMapper;
    private final RequestDtoMapper<CollectionRequestDto, Collection> collectionRequestDtoMapper;
    private final CollectionService collectionService;
    private final PodcastService podcastService;
    private final SearchCollection searchCollection;

    public CollectionController(RequestDtoMapper<PodcastRequestDto, Podcast>
                                        podcastRequestDtoMapper,
                                ResponseDtoMapper<CollectionResponseDto, Collection>
                                        collectionResponseDtoMapper,
                                RequestDtoMapper<CollectionRequestDto, Collection>
                                        collectionRequestDtoMapper,
                                CollectionService collectionService,
                                PodcastService podcastService,
                                SearchCollection searchCollection) {
        this.podcastRequestDtoMapper = podcastRequestDtoMapper;
        this.collectionResponseDtoMapper = collectionResponseDtoMapper;
        this.collectionRequestDtoMapper = collectionRequestDtoMapper;
        this.collectionService = collectionService;
        this.podcastService = podcastService;
        this.searchCollection = searchCollection;
    }

    @PostMapping
    public CollectionResponseDto savePodcast(@ModelAttribute @RequestBody
                                             PodcastRequestDto requestDto,
                                             @RequestParam String collectionName) {
        return collectionResponseDtoMapper.mapToDto(
                collectionService.addPodcastByName(
                        podcastService.save(podcastRequestDtoMapper
                                .mapToModel(requestDto)), collectionName));
    }

    @GetMapping
    public List<CollectionSearchResponseDto> findByWords(@RequestParam String text) {
        return searchCollection.findByWords(text);
    }

    @PostMapping("/create")
    public CollectionResponseDto createCollection(@RequestBody
                                                      CollectionRequestDto collectionRequestDto) {
        return collectionResponseDtoMapper.mapToDto(
                collectionService.create(
                        collectionRequestDtoMapper.mapToModel(collectionRequestDto)));
    }
}
