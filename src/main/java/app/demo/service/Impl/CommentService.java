package app.demo.service.Impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import app.demo.dto.req.CommentRequest;
import app.demo.dto.res.CommentResponse;
import app.demo.exception.ResourceNotFoundException;
import app.demo.filter.CommentFilter;
import app.demo.mapper.CommentMapper;
import app.demo.modal.Account;
import app.demo.modal.Comment;
import app.demo.payload.PaginationResponse;
import app.demo.repository.CommentRepository;
import app.demo.service.Iface.ICommentService;

@Service
public class CommentService implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public CommentResponse findById(String id) {
        Comment comment = commentRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        return commentMapper.toResponse(comment);
    }

    @Override
    public PaginationResponse<List<CommentResponse>> findAll(Pageable pageable) {

        Page<Comment> page = commentRepository.findAll(pageable);

        List<CommentResponse> commentResponses = commentMapper.toResponseList(page.getContent());

        return PaginationResponse.<List<CommentResponse>>builder()
                .data(commentResponses)
                .currentPage(page.getNumber())
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    @Override
    public CommentResponse create(CommentRequest entity, UserDetails userDetails) {

        Comment comment = commentMapper.toEntity(entity);

        Account author = new Account();
        author.setId(UUID.fromString(userDetails.getUsername()));

        comment.setAuthor(author);

        return commentMapper.toResponse(commentRepository.save(comment));
    }

    @Override
    public CommentResponse update(String id, CommentRequest entity, UserDetails userDetails) {

        Comment existingComment = commentRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("not found comment"));

        if (!String.valueOf(existingComment.getAuthor().getId()).equals(userDetails.getUsername())) {
            throw new AccessDeniedException("you do not have permission");
        }

        existingComment.setContent(entity.getContent());

        return commentMapper.toResponse(commentRepository.save(existingComment));
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void deleteById(String id, UserDetails userDetails) {
        Comment existingComment = commentRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("not found comment"));

        if (!String.valueOf(existingComment.getAuthor().getId()).equals(userDetails.getUsername())) {
            throw new AccessDeniedException("you do not have permission");
        }

        commentRepository.delete(existingComment);
    }

    @Override
    public PaginationResponse<List<CommentResponse>> findAll(Pageable pageable, String articleId) {

        Specification<Comment> specification = CommentFilter.commentFilter(articleId);

        Page<Comment> page = commentRepository.findAll(specification, pageable);

        List<CommentResponse> commentResponses = commentMapper.toResponseList(page.getContent());

        return PaginationResponse.<List<CommentResponse>>builder()
                .data(commentResponses)
                .currentPage(page.getNumber())
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

}
