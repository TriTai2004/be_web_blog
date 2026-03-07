package app.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.demo.dto.req.ArticleRequest;
import app.demo.service.Iface.IArticleService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private IArticleService articleService;
    
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(Pageable pageable){
        return ResponseEntity.ok(articleService.findAll(pageable));
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(
        @PathVariable(name = "id") String id){

        return ResponseEntity.ok(articleService.findById(id));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<?> findBySlug ( 
        @PathVariable(name = "slug") String slug
    ){
        return ResponseEntity.ok(articleService.findBySlug(slug));
    }

    @PostMapping
    public ResponseEntity<?> create( 
        @RequestBody @Valid ArticleRequest articleRequest,
        @AuthenticationPrincipal UserDetails userDetails
    ){

        return ResponseEntity.ok(articleService.create(articleRequest, userDetails));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update (
        @PathVariable(name = "id") String id,
        @RequestBody @Valid ArticleRequest articleRequest,
        @AuthenticationPrincipal UserDetails userDetails

    ){
        return ResponseEntity.ok(articleService.update(id, articleRequest, userDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
        @PathVariable(name = "id") String id
    ){
        articleService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
