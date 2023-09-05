package br.senai.sc.supertrunfo.security.util;

import br.senai.sc.supertrunfo.security.model.User;
import br.senai.sc.supertrunfo.security.repository.UserRepository;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    @Autowired
    JWTUtil(UserRepository userRepository) {
        JWTUtil.userRepository = userRepository;
    }

    private static UserRepository userRepository;

    private static final String senha = "c127a7b6adb013a5ff879ae71afa62afa4b4ceb72afaa54711dbcde67b6dc325";

    public static String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(senha);
        return JWT.create().withIssuer("WEG")
                .withSubject(user.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + 1000 * 60 * 30))
                .sign(algorithm);
    }

    public static User getUsuario(String token) {
        String username = JWT.decode(token).getSubject();
        System.out.println(username);
        return userRepository.findByUsuario_Email(username);
    }

}
