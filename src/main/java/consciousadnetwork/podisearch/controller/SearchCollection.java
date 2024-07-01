package consciousadnetwork.podisearch.controller;

import consciousadnetwork.podisearch.dto.response.CollectionSearchResponseDto;
import consciousadnetwork.podisearch.service.SearchingService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchCollection {

    private final SearchingService searchingService;

    public SearchCollection(SearchingService searchingService) {
        this.searchingService = searchingService;
    }

    @GetMapping
    public List<CollectionSearchResponseDto> findByWords(@RequestParam String text) {
        return searchingService.search(text);
    }
}
