package app.demo.mapper;

import java.util.List;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import app.demo.dto.req.CommentRequest;
import app.demo.dto.res.CommentResponse;
import app.demo.modal.Article;
import app.demo.modal.Comment;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    
    @Mapping(target="article", source = ".", qualifiedByName = "mapArticle")
    @Mapping(target = "parent", source = ".", qualifiedByName = "mapParent")
    Comment toEntity (CommentRequest req);

    @Named("mapArticle")
    default Article mapArticle(CommentRequest req){
        if(req.getArticleId() != null && !req.getArticleId().isBlank()){
            Article article = new Article();
            article.setId(UUID.fromString(req.getArticleId()));
            return article;
        }
        return null;
    }

    @Named("mapParent")
    default Comment mapParent(CommentRequest req) {

        if(req.getParentId() != null && !req.getArticleId().isBlank()){
            Comment parent = new Comment();
            parent.setId( UUID.fromString( req.getParentId() ) );
            return parent;
        }

        return null;
    }

    @Mapping(target = "articleId", source = "comment.article.id")
    @Mapping(target = "authorId", source =  "comment.author.id")
    @Mapping(target = "parentId", source = "comment.parent.id")
    @Mapping(target = "authorName", source = "comment.author.fullname")
    @Mapping(target = "authorAvatar", source = "comment.author.avatar")
    CommentResponse toResponse(Comment comment);


    List<CommentResponse> toResponseList (List<Comment> comments);



}
