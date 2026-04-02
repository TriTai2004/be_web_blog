package app.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import app.demo.dto.req.LikeCommentRequest;
import app.demo.dto.res.LikeCommentResponse;
import app.demo.modal.LikeComment;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LikeCommentMapper {
    
    @Mapping(target = "comment.id", source = "commentId")
    LikeComment toEntity(LikeCommentRequest req);

    @Mapping(target = "commentId", source = "comment.id")
    @Mapping(target = "authorId", source = "author.id")
    LikeCommentResponse toResponse(LikeComment likeComment);



}
