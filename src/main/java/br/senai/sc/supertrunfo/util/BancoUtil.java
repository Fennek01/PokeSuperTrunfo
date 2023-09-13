package br.senai.sc.supertrunfo.util;

import br.senai.sc.supertrunfo.model.enums.Perfil;
import br.senai.sc.supertrunfo.model.entity.Usuario;
import br.senai.sc.supertrunfo.repository.UsuarioRepository;
import br.senai.sc.supertrunfo.security.model.User;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class BancoUtil {

    private UsuarioRepository usuarioRepository;

    @PostConstruct
    public void popularBanco() {
        usuarioRepository.deleteAll();

        Usuario personAdmin = new Usuario();
        personAdmin.setNome("admin");
        personAdmin.setEmail("Admin@weg.net");
        personAdmin.setSobrenome("admin");
        personAdmin.setPassword(new BCryptPasswordEncoder().encode("admin"));
        personAdmin.setPerfis(List.of(Perfil.ADMIN));
        usuarioRepository.save(personAdmin);

        /*ADMIN*/
        User userAdmin = new User();
        userAdmin.setUsuario(personAdmin);
        userAdmin.setAccountNonExpired(true);
        userAdmin.setAccountNonLocked(true);
        userAdmin.setCredentialsNonExpired(true);
        userAdmin.setEnabled(true);

    }

}
