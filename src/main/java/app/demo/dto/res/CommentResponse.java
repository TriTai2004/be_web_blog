package app.demo.dto.res;

import java.sql.Date;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentResponse {
    
    private UUID id;
    private String content;
    private String authorName;
    private String authorAvatar;
    private Date createdAt;
    private Date updatedAt;
    private UUID articleId;
    private UUID authorId;
    private UUID parentId;
    private int totalReplies;
    private int totalLikes;
    private boolean likedByCurrentUser;
    private String parentAuthorName;
    

}
