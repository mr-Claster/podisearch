package consciousadnetwork.podisearch.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class CollectionSearchResponseDto {

    private String name;
    private String urlToImg;
    private List<PodcastSearchResponseDto> podcasts;
    private LocalDateTime published;
    private String viewUrl;

    public CollectionSearchResponseDto() {
        podcasts = new ArrayList<>();
    }
}
