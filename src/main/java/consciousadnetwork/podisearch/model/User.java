package consciousadnetwork.podisearch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.LinkedList;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    public User() {
        this.roles = new LinkedList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String profileImage;
    private String password;
    private String email;
    @ManyToMany
    private List<Role> roles;
    @ManyToOne
    private Provider provider;
}
