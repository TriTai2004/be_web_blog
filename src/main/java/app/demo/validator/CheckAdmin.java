package app.demo.validator;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;

public class CheckAdmin {
    
    public static void CheckAdminRole(UserDetails userDetails) {
        if (!userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            throw new AccessDeniedException("403 Forbidden");
        }
        
    }

}
