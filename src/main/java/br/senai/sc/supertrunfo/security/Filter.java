package br.senai.sc.supertrunfo.security;

import br.senai.sc.supertrunfo.model.entity.Usuario;
import br.senai.sc.supertrunfo.security.util.CookieUtil;
import br.senai.sc.supertrunfo.security.util.JWTUtil;
import com.auth0.jwt.exceptions.JWTDecodeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class Filter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!rotaPrivada(request.getRequestURI())) {
            try {
                String token = CookieUtil.getToken(request);
                Usuario user = JWTUtil.getUsuario(token);
                response.addCookie(CookieUtil.create(user));
                Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JWTDecodeException e) {
                System.out.println("Token inválido");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            } catch (CookieNotFound cookieNaoEncontrado) {
                System.out.println(cookieNaoEncontrado.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean rotaPrivada(String url) {
        return url.startsWith("/login");
    }

}
