package br.senai.sc.supertrunfo.security;

import br.senai.sc.supertrunfo.model.entity.Usuario;
import br.senai.sc.supertrunfo.security.model.User;
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
        // O método rotaPrivada verifica se a rota é privada ou não
        if (!rotaPrivada(request.getRequestURI())) {
            try {
                String token = CookieUtil.getToken(request);
                User user = JWTUtil.getUsuario(token);
                response.addCookie(CookieUtil.create(user));
                // Ele lê as informações do usuário e cria um objeto de autenticação
                Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
                // A utilização do getContext é para que o Spring Security consiga recuperar o usuário autenticado,
                // e receber suas credenciais
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
        // O doFilter é o responsável por dar continuidade ao fluxo da requisição
        filterChain.doFilter(request, response);
    }

    private boolean rotaPrivada(String url) {
        return url.startsWith("/login");
    }

}
