package br.senai.sc.supertrunfo.util;

import br.senai.sc.supertrunfo.model.entity.Perfil;
import br.senai.sc.supertrunfo.model.entity.Usuario;
import br.senai.sc.supertrunfo.repository.UsuarioRepository;
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
        personAdmin.setAuthorities(List.of(Perfil.ADMIN));
        personAdmin.setAccountNonExpired(true);
        personAdmin.setAccountNonLocked(true);
        personAdmin.setCredentialsNonExpired(true);
        personAdmin.setEnabled(true);
        usuarioRepository.save(personAdmin);

    }

}
