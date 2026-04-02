package app.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.demo.dto.req.LikeCommentRequest;
import app.demo.dto.res.LikeCommentResponse;
import app.demo.service.Iface.ILikeCommentService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/like-comments")
public class LikeCommentController {

    @Autowired
    private ILikeCommentService iLikeCommentService;

    @PostMapping
    public ResponseEntity<?> likeOrDislike(@Valid @RequestBody LikeCommentRequest likeCommentRequest) {

        LikeCommentResponse response = iLikeCommentService.likeOrDislike(likeCommentRequest);
        if (response == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);

    }

}
