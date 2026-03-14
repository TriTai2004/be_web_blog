package app.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import app.demo.dto.req.LikeRequest;
import app.demo.dto.res.LikeResponse;
import app.demo.modal.Like;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LikeMapper {

    @Mapping(target = "articleId", source = "like.article.id")
    @Mapping(target = "authorId", source = "like.author.id")
    LikeResponse toResponse (Like like);

    @Mapping(target = "article.id", source = "likeRequest.articleId")
    @Mapping(target = "author.id", source = "likeRequest.authorId")
    Like toEntity (LikeRequest likeRequest);
    
    
}
