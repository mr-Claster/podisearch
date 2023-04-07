package consciousadnetwork.podisearch.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class PodcastResponseDto {
    private String id;
    private String title;
    private List<WordResponseDto> words;
    private String urlToImg;
    private LocalDateTime published;
}
