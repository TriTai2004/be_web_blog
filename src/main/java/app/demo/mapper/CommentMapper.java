package app.demo.mapper;

import java.util.List;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.core.context.SecurityContextHolder;

import app.demo.dto.req.CommentRequest;
import app.demo.dto.res.CommentResponse;
import app.demo.modal.Article;
import app.demo.modal.Comment;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    @Mapping(target = "article", source = ".", qualifiedByName = "mapArticle")
    @Mapping(target = "parent", source = ".", qualifiedByName = "mapParent")
    Comment toEntity(CommentRequest req);

    @Named("mapArticle")
    default Article mapArticle(CommentRequest req) {
        if (req.getArticleId() != null && !req.getArticleId().isBlank()) {
            Article article = new Article();
            article.setId(UUID.fromString(req.getArticleId()));
            return article;
        }
        return null;
    }

    @Named("mapParent")
    default Comment mapParent(CommentRequest req) {

        if (req.getParentId() != null && !req.getArticleId().isBlank()) {
            Comment parent = new Comment();
            parent.setId(UUID.fromString(req.getParentId()));
            return parent;
        }

        return null;
    }

    @Mapping(target = "articleId", source = "comment.article.id")
    @Mapping(target = "authorId", source = "comment.author.id")
    @Mapping(target = "parentId", source = "comment.parent.id")
    @Mapping(target = "authorName", source = ".", qualifiedByName = "mapAuthorName")
    @Mapping(target = "authorAvatar", source = "comment.author.avatar")
    @Mapping(target = "totalReplies", source = ".", qualifiedByName = "mapTotalReplies")
    @Mapping(target = "totalLikes", source = ".", qualifiedByName = "mapTotalLikes")
    @Mapping(target = "likedByCurrentUser", source = ".", qualifiedByName = "mapLikedByCurrentUser")
    @Mapping(target = "parentAuthorName", source = "comment.parent.author.fullname")
    CommentResponse toResponse(Comment comment);

    @Named("mapTotalReplies")
    default int mapTotalReplies(Comment comment) {
        if (comment.getReplies() != null) {
            return comment.getReplies().size();
        }
        return 0;
    }

    @Named("mapAuthorName")
    default String mapAuthorName(Comment comment) {
        if (comment.getAuthor() != null) {
            return comment.getAuthor().getFullname();
        }
        return null;
    }

    @Named("mapTotalLikes")
    default int mapTotalLikes(Comment comment) {
        if (comment.getLikeComments() != null) {
            return comment.getLikeComments().size();
        }
        return 0;
    }

    @Named("mapLikedByCurrentUser")
    default boolean mapLikedByCurrentUser(Comment comment) {

        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth.getName() == null) {
            return false;
        }

        UUID currentUserId;

        try {
            currentUserId = UUID.fromString(auth.getName());
        } catch (Exception e) {
            return false;
        }

        if (comment.getLikeComments() == null) {
            return false;
        }

        return comment.getLikeComments().stream()
                .anyMatch(like -> like.getAuthor().getId().equals(currentUserId));
    }

    List<CommentResponse> toResponseList(List<Comment> comments);

}
