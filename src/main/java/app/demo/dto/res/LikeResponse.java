package app.demo.dto.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikeResponse {
    private String id;
    private String authorId;
    private String articleId;
}
