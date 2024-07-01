package consciousadnetwork.podisearch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.LinkedList;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "marks")
public class Mark {
    public Mark() {
        this.users = new LinkedList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    private List<User> users;
    @Transient
    private Podcast podcast;
    private String podcastId;
    private Byte stars;
}
