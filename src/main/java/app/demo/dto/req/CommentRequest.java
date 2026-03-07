package app.demo.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentRequest {
    
    @NotBlank(message = "Content is required")
    private String content;

    @NotBlank(message = "Article is required")
    private String articleId;

    private String parentId;

}
