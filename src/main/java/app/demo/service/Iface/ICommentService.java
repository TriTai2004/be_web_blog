package app.demo.service.Iface;

import org.springframework.security.core.userdetails.UserDetails;

import app.demo.dto.req.CommentRequest;
import app.demo.dto.res.CommentResponse;

public interface ICommentService extends IService<CommentResponse, CommentRequest, String>{
    
    void deleteById(String id, UserDetails userDetails);
}
