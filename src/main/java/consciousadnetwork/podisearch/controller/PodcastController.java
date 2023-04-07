package consciousadnetwork.podisearch.controller;

import consciousadnetwork.podisearch.dto.request.PodcastRequestDto;
import consciousadnetwork.podisearch.dto.response.PodcastResponseDto;
import consciousadnetwork.podisearch.model.Podcast;
import consciousadnetwork.podisearch.service.PodcastService;
import consciousadnetwork.podisearch.service.mapper.RequestDtoMapper;
import consciousadnetwork.podisearch.service.mapper.ResponseDtoMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/podcasts")
public class PodcastController {
    private final PodcastService podcastService;
    private final ResponseDtoMapper<PodcastResponseDto, Podcast> podcastResponseDtoMapper;
    private final RequestDtoMapper<PodcastRequestDto, Podcast> podcastRequestDtoMapper;

    public PodcastController(PodcastService podcastService,
                             ResponseDtoMapper<PodcastResponseDto, Podcast>
                                     podcastResponseDtoMapper,
                             RequestDtoMapper<PodcastRequestDto, Podcast>
                                     podcastRequestDtoMapper) {
        this.podcastService = podcastService;
        this.podcastResponseDtoMapper = podcastResponseDtoMapper;
        this.podcastRequestDtoMapper = podcastRequestDtoMapper;
    }

    @GetMapping
    public List<PodcastResponseDto> findPodcastByText(@RequestParam String text) {
        return podcastService.findByText(text).stream()
                .map(podcastResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public PodcastResponseDto savePodcast(@ModelAttribute @RequestBody
                                              PodcastRequestDto requestDto) {
        return podcastResponseDtoMapper.mapToDto(
                podcastService.savePodcast(
                        podcastRequestDtoMapper.mapToModel(requestDto)));
    }

    @PostMapping("/{id}")
    public PodcastResponseDto updatePodcast(@PathVariable String id,
                                            @ModelAttribute PodcastRequestDto requestDto) {
        Podcast podcast = podcastRequestDtoMapper.mapToModel(requestDto);
        podcast.setId(id);
        return podcastResponseDtoMapper.mapToDto(
                podcastService.savePodcast(podcast));
    }
}
