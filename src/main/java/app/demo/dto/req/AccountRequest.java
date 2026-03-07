package app.demo.dto.req;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountRequest {

    @NotBlank
    private String fullname;
    @NotBlank
    private String avatar;
}
