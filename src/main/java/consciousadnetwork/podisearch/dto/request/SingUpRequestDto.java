package consciousadnetwork.podisearch.dto.request;

import lombok.Data;

@Data
public class SingUpRequestDto {

    private String email;
    private String password;
    private String confirmPassword;
}
