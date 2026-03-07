package app.demo.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticleRequest {
    
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private String thumbnail;

    @NotBlank
    private String categoryId;

    
}
