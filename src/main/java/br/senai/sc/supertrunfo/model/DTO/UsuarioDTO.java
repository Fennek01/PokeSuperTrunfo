package br.senai.sc.supertrunfo.model.DTO;

import br.senai.sc.supertrunfo.model.entity.Carta;
import br.senai.sc.supertrunfo.model.entity.Perfil;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private String nome, sobrenome, email, password;

    private List<Perfil> perfis;

    private List<Carta> cartas;

}
