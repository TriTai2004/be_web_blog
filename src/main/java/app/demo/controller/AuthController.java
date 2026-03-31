package app.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.demo.dto.req.LoginRequest;
import app.demo.dto.req.RefreshTokenRequest;
import app.demo.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService  authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req, HttpServletRequest request, HttpServletResponse response) {

        return authService.login(req.getEmail(), req.getPassword(), request, response);

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid LoginRequest req, HttpServletRequest request, HttpServletResponse response) {
        return authService.register(req.getEmail(), req.getPassword(), request, response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        System.out.println("Received refresh token: " + refreshTokenRequest.getRefreshToken());
        // Implement refresh token logic here
        return authService.refreshToken(refreshTokenRequest.getRefreshToken());
    }

  
}
