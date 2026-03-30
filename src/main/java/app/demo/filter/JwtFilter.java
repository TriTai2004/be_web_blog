package app.demo.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import app.demo.util.JwtUtil;
import app.demo.config.UserDetailConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailConfig userDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        System.out.println("Request path: " + request.getServletPath());
        String path = request.getServletPath();
            return path.equals("/api/auth/login")
        || path.equals("/api/auth/register")
        || path.equals("/api/auth/refresh");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String token = null;

        // Lấy token từ cookie
        if (request.getCookies() != null) {
            for (var cookie : request.getCookies()) {
                if ("accessToken".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }

        // Không có token cho qua
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 1. Giải mã token
            String email = jwtUtil.extractEmail(token);

            // 2. Load user
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // 3. Tạo auth
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());

            // 4. Set context
            SecurityContextHolder.getContext().setAuthentication(auth);

            System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());

        } catch (Exception e) {
            System.out.println("JWT Error: " + e.getMessage());
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

}
