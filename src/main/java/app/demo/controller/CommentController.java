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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.demo.dto.req.CommentRequest;
import app.demo.service.Iface.ICommentService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private ICommentService commentService;
    
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(Pageable pageable,
        @RequestParam(name = "articleId", required = false) String articleId,
        @RequestParam(name = "parentId", required = false) String parentId
    ){

        return ResponseEntity.ok(commentService.findAll(pageable, articleId, parentId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById( @PathVariable(name = "id") String id){

        return ResponseEntity.ok(commentService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<?> createComment(
        @RequestBody @Valid CommentRequest commentRequest,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        // Implementation for creating a comment
        return ResponseEntity.ok(commentService.create(commentRequest, userDetails));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> udpateEntity(
        @PathVariable(name = "id") String id,
        @RequestBody @Valid CommentRequest commentRequest,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        // Implementation for updating a comment
        return ResponseEntity.ok(commentService.update(id, commentRequest, userDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(
        @PathVariable(name = "id") String id,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        commentService.deleteById(id, userDetails);
        return ResponseEntity.noContent().build();
    }

}
