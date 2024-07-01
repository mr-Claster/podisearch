package consciousadnetwork.podisearch.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Podcast {

    private String title;
    private List<Word> words;
    private String urlToPodcast;
    private LocalDateTime published;
    private Long views;
    private Long numberOfMarks;
    private Double averageRating;
    private String priority;

    public Podcast() {
        words = new ArrayList<>();
    }
}
