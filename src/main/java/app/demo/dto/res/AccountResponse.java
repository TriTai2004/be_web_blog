package app.demo.dto.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponse {
    
    private String id;
    private String fullname;
    private String avatar;
    private String email;

}
