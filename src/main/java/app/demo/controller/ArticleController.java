package app.demo.controller;

import java.io.IOException;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import app.demo.dto.req.ArticleRequest;
import app.demo.service.Iface.IArticleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private IArticleService articleService;
    
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(Pageable pageable,
        @RequestParam(name = "search", required = false) String search,
        @RequestParam(name = "slugCategory", required = false) String slugCategory
    ){
        return ResponseEntity.ok(articleService.findAll(search, slugCategory, pageable));
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

    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadImage(
        @AuthenticationPrincipal UserDetails userDetails,
        @RequestParam("file") MultipartFile file
    )throws IOException{
        return ResponseEntity.ok(articleService.uploadImage(file, userDetails));
    }

    @DeleteMapping("/delete-image/{id}")
    public ResponseEntity<?> deleteImage(
        @AuthenticationPrincipal UserDetails userDetails,
        @PathVariable("id") String publicId
    )throws IOException{
        return ResponseEntity.ok(articleService.deleteImage(publicId, userDetails));
    }

    @GetMapping("/findAll/popular")
    public ResponseEntity<?> findAllHotTop(Pageable pageable
        
    ){
        return ResponseEntity.ok(articleService.findAllPopular(pageable));
    }

    @PutMapping("/views/{id}")
    public ResponseEntity<?> viewArticle(@PathVariable("id") String id,
        HttpServletRequest request
) {
        return ResponseEntity.ok(articleService.updateViews(id, request));
    }
}
