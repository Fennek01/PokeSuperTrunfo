package br.senai.sc.supertrunfo.security.util;

import br.senai.sc.supertrunfo.model.entity.Usuario;
import br.senai.sc.supertrunfo.repository.UsuarioRepository;
import br.senai.sc.supertrunfo.security.model.User;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    private static final String senha = "c127a7b6adb013a5ff879ae71afa62afa4b4ceb72afaa54711dbcde67b6dc325";

    private static UsuarioRepository usuarioRepository;
    @Autowired
    JWTUtil(UsuarioRepository userRepository) {
        JWTUtil.usuarioRepository = userRepository;
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
        String id = JWT.decode(token).getSubject();

        // Verificar se a string id é numérica antes de fazer a conversão
        if (!isNumeric(id)) {
            throw new IllegalArgumentException("ID não é um valor numérico válido");
        }

        Long idLong = Long.parseLong(id);
        Usuario usuario = usuarioRepository.findById(idLong).orElseThrow();
        return new User(usuario);
    }

    // Método para verificar se uma string é numérica
    private static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Long.parseLong(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}
