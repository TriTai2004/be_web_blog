package app.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import app.demo.modal.Comment;

public interface CommentRepository extends JpaRepository<Comment, UUID>{
    
}
