package app.demo.service.Iface;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import app.demo.dto.req.CommentRequest;
import app.demo.dto.res.CommentResponse;
import app.demo.payload.PaginationResponse;

public interface ICommentService extends IService<CommentResponse, CommentRequest, String>{
    
    void deleteById(String id, UserDetails userDetails);
    PaginationResponse<List<CommentResponse>> findAll(Pageable pageable, String articleId, String parentId);
}
