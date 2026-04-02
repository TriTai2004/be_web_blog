package app.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import app.demo.modal.LikeComment;

public interface LikeCommentRepository extends JpaRepository<LikeComment, UUID>{
    
    LikeComment findByCommentIdAndAuthorId(UUID commentId, UUID authorId);
}
