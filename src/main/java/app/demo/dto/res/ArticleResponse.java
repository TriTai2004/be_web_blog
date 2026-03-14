package app.demo.dto.res;

import java.util.Date;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticleResponse {

    private UUID id;
    private String title;
    private String content;
    private float views;
    private String thumbnail;
    private String slug;
    private Integer likes;
    
    private Date createdAt;
    private Date updatedAt;
    
    
}
