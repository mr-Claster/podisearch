package consciousadnetwork.podisearch.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class PodcastResponseDto {

    private String title;
    private List<WordResponseDto> words;
    private LocalDateTime published;
}
