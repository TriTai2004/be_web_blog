package app.demo.service.Iface;


import app.demo.dto.req.LikeCommentRequest;
import app.demo.dto.req.LikeRequest;
import app.demo.dto.res.LikeCommentResponse;
import app.demo.dto.res.LikeResponse;

public interface ILikeCommentService extends IService<LikeResponse, LikeRequest, String> {

    LikeCommentResponse likeOrDislike(LikeCommentRequest likeCommentRequest);

    LikeCommentResponse findByCommentIdAndAuthorId(String commentId, String authorId);
}
