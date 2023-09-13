package br.senai.sc.supertrunfo.security.util;

import br.senai.sc.supertrunfo.model.entity.Usuario;
import br.senai.sc.supertrunfo.repository.UsuarioRepository;
import br.senai.sc.supertrunfo.security.model.User;
import br.senai.sc.supertrunfo.security.repository.UserRepository;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    private static final String senha = "c127a7b6adb013a5ff879ae71afa62afa4b4ceb72afaa54711dbcde67b6dc325";

    private static UserRepository userRepository;
    @Autowired
    JWTUtil(UserRepository userRepository) {
        JWTUtil.userRepository = userRepository;
    }


    public static String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(senha);
        return JWT.create().withIssuer("WEG")
                .withSubject(user.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + 1000 * 60 * 30))
                .sign(algorithm);
    }

    public static User getUsuario(String token) {
        String email = JWT.decode(token).getSubject();
        return userRepository.findByUsuario_Email(email);
    }

}
