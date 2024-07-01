package consciousadnetwork.podisearch.model;

import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("collection")
public class Collection {

    @Id
    private String id;
    private List<Podcast> podcasts;
    private String name;
    private String urlToImg;
    private LocalDateTime published;
    private String viewUrl;

    public Collection() {
        podcasts = new ArrayList<>();
    }
}
