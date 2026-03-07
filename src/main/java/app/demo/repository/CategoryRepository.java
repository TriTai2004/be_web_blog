package app.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import app.demo.modal.Category;

public interface CategoryRepository extends JpaRepository<Category, String>{
    
    Category findBySlug(String slug);
    List<Category> findByNameContainingIgnoreCase(String name);
    boolean existsBySlug(String slug);

    Category findById(UUID id);

    void deleteById (UUID id);
}
