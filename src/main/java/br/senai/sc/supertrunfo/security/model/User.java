package br.senai.sc.supertrunfo.security.model;

import br.senai.sc.supertrunfo.model.entity.Perfil;
import br.senai.sc.supertrunfo.model.entity.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
public class User implements UserDetails {

    private Usuario usuario;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public User(Usuario usuario) {
        this.usuario = usuario;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

    public String getUsername() {
        return usuario.getEmail();
    }

    public String getPassword() {
        return usuario.getPassword();
    }

    public List<Perfil> getAuthorities() {
        return usuario.getPerfis();
    }


}
