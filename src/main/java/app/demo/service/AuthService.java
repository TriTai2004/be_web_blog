package app.demo.service;

import java.util.Base64;
import java.util.Map;
import java.util.UUID;

import org.mapstruct.control.MappingControl.Use;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import app.demo.util.JwtUtil;
import app.demo.dto.res.AccountResponse;
import app.demo.dto.res.LoginResponse;
import app.demo.dto.res.UserResponse;
import app.demo.exception.ResourceNotFoundException;
import app.demo.modal.Account;
import app.demo.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;
    private final JwtUtil jwtUtil;

    public LoginResponse login(String email, String password) {

        Account account = accountRepository.findByEmail(email);
        if (account != null && account.getPassword().equals(Base64.getEncoder().encodeToString(password.getBytes()))) {

            UserResponse userResponse = UserResponse.builder()
                    .id(account.getId())
                    .email(account.getEmail())
                    .role(account.getRole())
                    .build();
            String token = jwtUtil.generateToken(account.getEmail(), account.getRole());
            LoginResponse loginResponse = LoginResponse.builder()
                    .accessToken(token)
                    .refreshToken(jwtUtil.generateRefreshToken(account.getEmail()))
                    .expiresIn(jwtUtil.extractExpiration(token).getTime())
                    .tokenType("Bearer")
                    .user(userResponse)
                    .build();

            return loginResponse;
        } else {
            return null;
        }

    }

    public ResponseEntity<?> register(String email, String password) {

        Account existingAccount = accountRepository.findByEmail(email);
        if (existingAccount != null) {
            return ResponseEntity.status(409).body("Email already in use");
        }

        Account account = new Account();
        account.setEmail(email);
        account.setPassword(Base64.getEncoder().encodeToString(password.getBytes()));
        account.setRole("USER");
        account = accountRepository.save(account);

        // Sinh token ngay sau khi tạo account
        String token = jwtUtil.generateToken(account.getEmail(), account.getRole());

        UserResponse userResponse = UserResponse.builder()
                .id(account.getId())
                .email(account.getEmail())
                .role(account.getRole())
                .build();

        LoginResponse loginResponse = LoginResponse.builder()
                .accessToken(token)
                .refreshToken(jwtUtil.generateRefreshToken(account.getEmail()))
                .expiresIn(jwtUtil.extractExpiration(token).getTime())
                .tokenType("Bearer")
                .user(userResponse)
                .build();

        return ResponseEntity.ok().body(loginResponse);
    }

    public ResponseEntity<?> refreshToken(String refreshToken) {

        if (refreshToken.isBlank()) {
            return ResponseEntity.status(400).body("Refresh token is required");
        }

        if (jwtUtil.isTokenExpired(refreshToken, jwtUtil.getREFRESH_SECRET())) {
            return ResponseEntity.status(401).body("Refresh token expired");
        }

        String email = jwtUtil.extractEmailFromRefreshToken(refreshToken);

        Account account = accountRepository.findByEmail(email);

        String newAccessToken = jwtUtil.generateToken(email, account.getRole());

        // Implement refresh token logic here
        return ResponseEntity.ok(Map.of(
                "accessToken", newAccessToken,
                "tokenType", "Bearer",
                "expiresIn", jwtUtil.extractExpiration(newAccessToken).getTime()));
    }

    public Map<String, Object> getMe() {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        // check null + chưa login
        if (authentication == null || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException("Unauthorized");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // lấy user từ DB
        Account account = accountRepository.findById(UUID.fromString(userDetails.getUsername()))
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));


        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(auth -> auth.getAuthority())
                .orElse("USER"); 

        return Map.of(
            "id", account.getId(),
            "email", account.getEmail(),
            "fullname", account.getFullname(),
            "avatar", account.getAvatar(),
            "role", role
        );
    }
}
