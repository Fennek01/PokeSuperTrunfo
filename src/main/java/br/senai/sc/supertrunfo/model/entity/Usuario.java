package br.senai.sc.supertrunfo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sobrenome, nome, email, password;

    @Enumerated(EnumType.STRING)
    private List<Perfil> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    @ManyToMany
    @JoinTable(name = "carta_usuario",
            joinColumns = @JoinColumn(name = "carta_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private List<Carta> cartas;

    @Override
    public String getUsername() {
        return this.email;
    }
}
