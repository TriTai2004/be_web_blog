package app.demo.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import app.demo.modal.Account;
import app.demo.repository.AccountRepository;
import app.demo.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    private final AccountRepository accountRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        String email = oauthUser.getAttribute("email");

        Account account = accountRepository.findByEmail(email);

        if (account == null) {
            account = new Account();
            account.setEmail(email);
            account.setFullname(oauthUser.getAttribute("name"));
            account.setAvatar(oauthUser.getAttribute("picture"));
            account.setRole("USER");
            accountRepository.save(account);
        }


        String accessToken = jwtUtil.generateToken(account.getEmail(), account.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(account.getEmail());

        response.addHeader("Set-Cookie",
                "accessToken=" + accessToken + "; HttpOnly; Path=/; Max-Age=86400; SameSite=Lax");

        response.addHeader("Set-Cookie",
                "refreshToken=" + refreshToken + "; HttpOnly; Path=/; Max-Age=604800; SameSite=Lax");

        response.sendRedirect("http://localhost:5173");

    }
}