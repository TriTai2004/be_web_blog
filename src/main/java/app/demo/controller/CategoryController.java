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

import app.demo.dto.req.CategoryRequest;
import app.demo.service.Iface.ICategoryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
    

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(
        Pageable pageable
    ) {
        return ResponseEntity.ok(categoryService.findAll(pageable));
    }   


    @GetMapping("/{slug}")
    public ResponseEntity<?> findBySlug(
        @PathVariable String slug
    ) {
        return ResponseEntity.ok(categoryService.findBySlug(slug));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
        @PathVariable String id,
        @Valid @RequestBody CategoryRequest request,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(categoryService.update(id, request, userDetails));
    }

    @PostMapping
    public ResponseEntity<?> create(
        @Valid @RequestBody CategoryRequest request,
        @AuthenticationPrincipal UserDetails userDetails

    ) {
        return ResponseEntity.ok(categoryService.create(request, userDetails));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
        @PathVariable String id
    ) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }



}
