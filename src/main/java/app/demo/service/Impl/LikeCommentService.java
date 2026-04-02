package app.demo.service.Impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import app.demo.dto.req.LikeCommentRequest;
import app.demo.dto.req.LikeRequest;
import app.demo.dto.res.LikeCommentResponse;
import app.demo.dto.res.LikeResponse;
import app.demo.exception.ResourceNotFoundException;
import app.demo.mapper.LikeCommentMapper;
import app.demo.modal.Account;
import app.demo.modal.Comment;
import app.demo.modal.LikeComment;
import app.demo.payload.PaginationResponse;
import app.demo.repository.AccountRepository;
import app.demo.repository.CommentRepository;
import app.demo.repository.LikeCommentRepository;
import app.demo.service.Iface.ILikeCommentService;

@Service
public class LikeCommentService implements ILikeCommentService {

    @Autowired
    private LikeCommentRepository likeCommentRepository;

    @Autowired
    private CommentRepository   commentRepository;

    @Autowired 
    private AccountRepository accountRepository;

    @Autowired
    private LikeCommentMapper likeCommentMapper;

    @Override
    public LikeResponse findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public PaginationResponse<List<LikeResponse>> findAll(Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public LikeResponse create(LikeRequest entity, UserDetails userDetails) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public LikeResponse update(String id, LikeRequest entity, UserDetails userDetails) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public LikeCommentResponse likeOrDislike(LikeCommentRequest likeCommentRequest) {

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        LikeComment likeComment = likeCommentRepository.findByCommentIdAndAuthorId(
                UUID.fromString(likeCommentRequest.getCommentId()), UUID.fromString(userId));

        if (likeComment != null) {

            likeCommentRepository.delete(likeComment);
        } else {
            Comment comment = commentRepository.findById(UUID.fromString(likeCommentRequest.getCommentId()))
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
            Account account = accountRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            likeComment = new LikeComment();

            likeComment.setAuthor(account);
            likeComment.setComment(comment);
            likeComment = likeCommentRepository.save(likeComment);
        }
        return likeCommentMapper.toResponse(likeComment);
    }

    @Override
    public LikeCommentResponse findByCommentIdAndAuthorId(String commentId, String authorId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByCommentIdAndAuthorId'");
    }

}
