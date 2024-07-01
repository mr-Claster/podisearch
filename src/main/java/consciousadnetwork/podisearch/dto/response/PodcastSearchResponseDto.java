package consciousadnetwork.podisearch.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class PodcastSearchResponseDto {

    private String title;
    private List<WordResponseDto> words;
    //private Double averageRating;
    private LocalDateTime published;

    public PodcastSearchResponseDto() {
        words = new ArrayList<>();
    }
}
