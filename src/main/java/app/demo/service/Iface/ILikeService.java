package app.demo.service.Iface;

import org.springframework.security.core.userdetails.UserDetails;

import app.demo.dto.req.LikeRequest;
import app.demo.dto.res.LikeResponse;

public interface ILikeService extends IService<LikeResponse, LikeRequest, String>{
    
    LikeResponse likeOrDislike(LikeRequest likeRequest, UserDetails userDetails);
    LikeResponse findByArticleIdAndAuthorId(String articleId, String authorId);
    
}
