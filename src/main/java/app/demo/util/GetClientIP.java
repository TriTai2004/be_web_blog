package app.demo.util;

import jakarta.servlet.http.HttpServletRequest;

public class GetClientIP {
    public static String getClientIp(HttpServletRequest request) {

        String xfHeader = request.getHeader("X-Forwarded-For");

        if (xfHeader == null) {
            return request.getRemoteAddr();
        }

        return xfHeader.split(",")[0];
    }
}
