package app.demo.dto.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private Long expiresIn;
    private UserResponse user;
    
}
