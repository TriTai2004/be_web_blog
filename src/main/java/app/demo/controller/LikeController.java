package app.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.demo.dto.req.LikeRequest;
import app.demo.service.Iface.ILikeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private ILikeService iLikeService;

    @GetMapping
    public ResponseEntity<?> findByIsLike(
            @RequestParam(name = "articleId") String articleId,
            @RequestParam(name = "authorId") String authorId) {

        return ResponseEntity.ok(iLikeService.findByArticleIdAndAuthorId(articleId, authorId));
    }

    @PostMapping
    public ResponseEntity<?> likeOrDislike(
            @RequestBody @Valid LikeRequest likeRequest,
            @AuthenticationPrincipal UserDetails userDetails
        ) {

        return ResponseEntity.ok(iLikeService.likeOrDislike(likeRequest, userDetails));
    }
}
