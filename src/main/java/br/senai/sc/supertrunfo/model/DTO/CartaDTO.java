package br.senai.sc.supertrunfo.model.DTO;

import br.senai.sc.supertrunfo.model.entity.Carta;
import br.senai.sc.supertrunfo.model.enums.Tipagem;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartaDTO {

    @NotNull
    @Enumerated(EnumType.STRING)
    private Tipagem tipagem1, tipagem2;
    @NotNull
    private String nome, descricao;

    private String imagem;

    private String imagemDaFrente, imagemDeTras, raridade;

    @NotNull
    private int vida, ataque, defesa, spAtaque, spDefesa, velocidade, total;

    private Long numPokedex;


}

