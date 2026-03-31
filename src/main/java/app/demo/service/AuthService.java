package app.demo.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.demo.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        private final PasswordEncoder passwordEncoder;

        public ResponseEntity<?> login(String email, String password,
                        HttpServletRequest request,
                        HttpServletResponse response) {

                String normalizedEmail = email.trim();

                Account account = accountRepository.findByEmail(normalizedEmail);

                if (account == null || !passwordEncoder.matches(password, account.getPassword())) {
                        return ResponseEntity.status(401)
                                        .body(Map.of("message", "Invalid email or password"));
                }

                String accessToken = jwtUtil.generateToken(account.getEmail(), account.getRole());
                String refreshToken = jwtUtil.generateRefreshToken(account.getEmail());

                response.addHeader("Set-Cookie",
                                "accessToken=" + accessToken + "; HttpOnly; Path=/; Max-Age=86400; SameSite=Lax");

                response.addHeader("Set-Cookie",
                                "refreshToken=" + refreshToken + "; HttpOnly; Path=/; Max-Age=604800; SameSite=Lax");

                return ResponseEntity.ok(UserResponse.builder()
                                .id(account.getId())
                                .email(account.getEmail())
                                .fullname(account.getFullname() != null ? account.getFullname() : "")
                                .avatar(account.getAvatar() != null ? account.getAvatar() : "")
                                .role(account.getRole())
                                .build());
        }

        public ResponseEntity<?> register(String email, String password,
                        HttpServletRequest request,
                        HttpServletResponse response) {

                String normalizedEmail = email.trim();

                if (password.length() < 6) {
                        return ResponseEntity.badRequest().body(Map.of("message", "Password too short"));
                }

                if (accountRepository.findByEmail(normalizedEmail) != null) {
                        return ResponseEntity.status(409).body(Map.of("message", "Email already in use"));
                }

                Account account = new Account();
                account.setEmail(normalizedEmail);
                account.setPassword(passwordEncoder.encode(password));
                account.setRole("USER");

                account = accountRepository.save(account);

                String accessToken = jwtUtil.generateToken(account.getEmail(), account.getRole());
                String refreshToken = jwtUtil.generateRefreshToken(account.getEmail());

                response.addHeader("Set-Cookie",
                                "accessToken=" + accessToken + "; HttpOnly; Path=/; Max-Age=86400; SameSite=Lax; ");

                response.addHeader("Set-Cookie",
                                "refreshToken=" + refreshToken + "; HttpOnly; Path=/; Max-Age=604800; SameSite=Lax; ");

                return ResponseEntity.ok(UserResponse.builder()
                                .id(account.getId())
                                .email(account.getEmail())
                                .fullname(account.getFullname() != null ? account.getFullname() : "")
                                .avatar(account.getAvatar() != null ? account.getAvatar() : "")
                                .role(account.getRole())
                                .build());
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

        public UserResponse getMe() {

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

                return UserResponse.builder()
                                .id(account.getId())
                                .email(account.getEmail())
                                .fullname(account.getFullname() != null ? account.getFullname() : "")
                                .avatar(account.getAvatar() != null ? account.getAvatar() : "")
                                .role(role)
                                .build();
        }
}
