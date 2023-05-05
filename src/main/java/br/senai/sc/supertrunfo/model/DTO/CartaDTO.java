package br.senai.sc.supertrunfo.model.DTO;

import br.senai.sc.supertrunfo.model.entity.Carta;
import br.senai.sc.supertrunfo.model.enums.Tipagem;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartaDTO {

    @NotNull
    @Enumerated(EnumType.STRING)
    private Tipagem tipagem1, tipagem2;
    @NotNull
    private String nome, imagem, descricao;
    @NotNull
    private int vida, ataque, defesa, spAtaque, spDefesa, velocidade, total;

    private Long numPokedex;


}

