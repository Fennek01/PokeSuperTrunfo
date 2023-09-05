package br.senai.sc.supertrunfo.util;

import br.senai.sc.supertrunfo.model.entity.Perfil;
import br.senai.sc.supertrunfo.model.entity.Usuario;
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

        Usuario admin = new Usuario();
        admin.setId(12345678910L);
        admin.setNome("Admin");
        admin.setSobrenome("Abreu");
        admin.setEmail("Admin@weg.net");

        Usuario usuario = new Usuario();
        usuario.setId(12345678912L);
        usuario.setNome("usuario");
        usuario.setSobrenome("Guilherme");
        usuario.setEmail("Usuario@weg.net");

        User user = new User();
        user.setPassword(new BCryptPasswordEncoder().encode("Mi7!"));
        user.setUsuario(usuario);
        user.setEnabled(true);
        user.setAuthorities(List.of(Perfil.JOGADOR));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        userRepository.save(user);

        User administrador = new User();
        administrador.setPassword(new BCryptPasswordEncoder().encode("Mi7?"));
        administrador.setUsuario(admin);
        administrador.setEnabled(true);
        administrador.setAuthorities(List.of(Perfil.ADMIN));
        administrador.setAccountNonExpired(true);
        administrador.setAccountNonLocked(true);
        administrador.setCredentialsNonExpired(true);
        userRepository.save(administrador);
    }

}
