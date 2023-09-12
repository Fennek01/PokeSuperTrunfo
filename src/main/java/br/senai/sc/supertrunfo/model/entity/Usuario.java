package br.senai.sc.supertrunfo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Usuario  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sobrenome, nome, email, password;

    @Enumerated(EnumType.STRING)
    private List<Perfil> perfis;

    @ManyToMany
    @JoinTable(name = "carta_usuario",
            joinColumns = @JoinColumn(name = "carta_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private List<Carta> cartas;


}
