package consciousadnetwork.podisearch.dto.response;

import consciousadnetwork.podisearch.model.Role;
import java.util.List;
import lombok.Data;

@Data
public class UserResponseDto {
    private String email;
    private List<Role> roles;
}
