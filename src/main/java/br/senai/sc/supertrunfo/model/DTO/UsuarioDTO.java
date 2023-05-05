package br.senai.sc.supertrunfo.model.DTO;

import br.senai.sc.supertrunfo.model.entity.Carta;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    @NotNull
    private String nome, sobrenome, senha, email;

    private List<Carta> cartas;

}
