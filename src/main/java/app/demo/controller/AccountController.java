package app.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import app.demo.dto.req.AccountRequest;
import app.demo.service.AuthService;
import app.demo.service.Iface.IAccountService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private AuthService authService;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(
            Pageable pageable,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(accountService.findAll(pageable, userDetails));
    }

    @GetMapping("/info")
    public ResponseEntity<?> getInfo(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(accountService.findById(userDetails.getUsername()));
    }

    @PutMapping
    public ResponseEntity<?> update(
            @Valid @ModelAttribute AccountRequest req,
            @RequestParam(value = "file", required = false) MultipartFile file

    ) throws IOException {
        return ResponseEntity.ok(accountService.update(req, file));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe() {
        return ResponseEntity.ok(authService.getMe());
    }
}
