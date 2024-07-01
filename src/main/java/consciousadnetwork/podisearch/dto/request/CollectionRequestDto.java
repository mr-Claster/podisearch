package consciousadnetwork.podisearch.dto.request;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CollectionRequestDto {

    private String name;
    private String urlToImg;
    private LocalDateTime published;
    private String viewUrl;
}
