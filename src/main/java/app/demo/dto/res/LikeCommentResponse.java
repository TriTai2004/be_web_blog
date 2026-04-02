package app.demo.dto.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikeCommentResponse {
    
    private String commentId;
    private String authorId;

}
