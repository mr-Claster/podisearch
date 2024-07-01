package consciousadnetwork.podisearch.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class CollectionResponseDto {

    private List<PodcastResponseDto> podcasts;
    private String name;
    private String urlToImg;
    private LocalDateTime published;
    private String viewUrl;
}
