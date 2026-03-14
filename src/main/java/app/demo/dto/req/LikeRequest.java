package app.demo.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikeRequest {
    @NotBlank(message = "Article is required")
    private String articleId;
    @NotBlank(message = "Author is required")
    private String authorId;
}
