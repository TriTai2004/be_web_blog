package app.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import app.demo.modal.Article;

public interface ArticleRepository extends JpaRepository<Article, UUID>{
    boolean existsBySlug(String slug);
    Article findBySlug(String slug);
}
