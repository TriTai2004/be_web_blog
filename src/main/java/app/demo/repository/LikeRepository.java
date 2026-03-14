package app.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import app.demo.modal.Like;

public interface LikeRepository extends JpaRepository<Like, UUID>{
    
    Like findByArticleIdAndAuthorId(UUID articleId, UUID authorId);

}
