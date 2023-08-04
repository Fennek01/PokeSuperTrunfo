package br.senai.sc.supertrunfo.util;

import br.senai.sc.supertrunfo.model.entity.Usuario;
import br.senai.sc.supertrunfo.security.model.User;
import br.senai.sc.supertrunfo.security.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class BancoUtil {

    private UserRepository userRepository;

    @PostConstruct
    public void popularBanco() {
        userRepository.deleteAll();

        Usuario user = new Usuario();
        user.setId(12345678910L);
        user.setNome("Admin");
        user.setSobrenome("Abreu");
        user.setEmail("Admin@weg.net");

        User usuario = new User();
        usuario.setPassword(new BCryptPasswordEncoder().encode("Mi7!"));
        usuario.setUsuario(user);
        usuario.setEnabled(true);
        usuario.setAuthorities(new ArrayList<>());
        usuario.setAccountNonExpired(true);
        usuario.setAccountNonLocked(true);
        usuario.setCredentialsNonExpired(true);
        userRepository.save(usuario);
    }

}
