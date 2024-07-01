package consciousadnetwork.podisearch.controller;

import consciousadnetwork.podisearch.dto.response.PodcastResponseDto;
import consciousadnetwork.podisearch.dto.response.PodcastSearchResponseDto;
import consciousadnetwork.podisearch.mapper.ResponseDtoMapper;
import consciousadnetwork.podisearch.model.Podcast;
import consciousadnetwork.podisearch.model.User;
import consciousadnetwork.podisearch.service.PodcastService;
import consciousadnetwork.podisearch.service.RatingService;
import consciousadnetwork.podisearch.service.UserService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/podcasts")
public final class PodcastController {

    private final PodcastService podcastService;
    private final RatingService ratingService;
    private final UserService userService;
    private final ResponseDtoMapper<PodcastResponseDto, Podcast> podcastResponseDtoMapper;

    public PodcastController(PodcastService podcastService,
                             RatingService ratingService,
                             UserService userService,
                             ResponseDtoMapper<PodcastResponseDto, Podcast>
                                     podcastResponseDtoMapper) {
        this.podcastService = podcastService;
        this.ratingService = ratingService;
        this.userService = userService;
        this.podcastResponseDtoMapper = podcastResponseDtoMapper;
    }

    @GetMapping
    public List<PodcastSearchResponseDto> findPodcastsByText(@RequestParam String text) {
        return podcastService.findByText(text);
    }

    @PostMapping("/{podcastId}/mark")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void markPodcast(@RequestBody byte mark,
                            @PathVariable String podcastName) {
        User activeUser = userService.getActiveUser();
        ratingService.markPodcast(mark, podcastName, activeUser);
    }
}
