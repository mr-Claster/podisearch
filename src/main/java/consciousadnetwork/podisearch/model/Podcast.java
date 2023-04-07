package consciousadnetwork.podisearch.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("podcasts")
public class Podcast {
    @Id
    private String id;
    private String title;
    private List<Word> words;
    private String urlToImg;
    private LocalDateTime published;
}
