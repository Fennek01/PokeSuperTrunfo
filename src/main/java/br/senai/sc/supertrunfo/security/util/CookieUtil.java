package br.senai.sc.supertrunfo.security.util;

import br.senai.sc.supertrunfo.model.entity.Usuario;
import br.senai.sc.supertrunfo.security.CookieNotFound;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.util.WebUtils;

public class CookieUtil {

    public static Cookie create(Usuario user) {
        String token = JWTUtil.generateToken(user);
        Cookie cookie = new Cookie("JWT", token);
        cookie.setPath("/");
        cookie.setMaxAge(1800);
        return cookie;
    }

    public static String getToken(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "JWT");
        if (cookie != null) {
            return cookie.getValue();
        }
        throw new CookieNotFound("JWT");
    }
}
