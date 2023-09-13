package br.senai.sc.supertrunfo.util;

import br.senai.sc.supertrunfo.model.entity.Perfil;
import br.senai.sc.supertrunfo.model.entity.Usuario;
import br.senai.sc.supertrunfo.repository.UsuarioRepository;
import br.senai.sc.supertrunfo.security.model.User;
import br.senai.sc.supertrunfo.security.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class BancoUtil {

    private UserRepository userRepository;

    @PostConstruct
    public void popularBanco() {
        userRepository.deleteAll();

        Usuario personAdmin = new Usuario();
        personAdmin.setNome("admin");
        personAdmin.setEmail("Admin@weg.net");
        personAdmin.setSobrenome("admin");

        /*ADMIN*/
        User userAdmin = new User();
        userAdmin.setPassword(new BCryptPasswordEncoder().encode("admin"));
        userAdmin.setAuthorities(List.of(Perfil.ADMIN));
        userAdmin.setUsuario(personAdmin);
        userAdmin.setAccountNonExpired(true);
        userAdmin.setAccountNonLocked(true);
        userAdmin.setCredentialsNonExpired(true);
        userAdmin.setEnabled(true);
        userRepository.save(userAdmin);

    }

}
