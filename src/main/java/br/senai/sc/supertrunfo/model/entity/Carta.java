package br.senai.sc.supertrunfo.model.entity;

import br.senai.sc.supertrunfo.model.enums.Tipagem;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Carta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive
    @Min(0)
    private Long id;

    private Long numPokedex;
    private String nome;
    private String imagem;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private Tipagem tipagem1;
    @Enumerated(EnumType.STRING)
    private Tipagem tipagem2;
    @Positive
    @Max(1000) @Min(0)
    private int total;
    @Positive
    @Max(255) @Min(0)
    private int vida;
    @Positive
    @Max(255) @Min(0)
    private int ataque;
    @Positive
    @Max(255) @Min(0)
    private int defesa;
    @Positive
    @Max(255) @Min(0)
    private int spAtaque;
    @Positive
    @Max(255) @Min(0)
    private int spDefesa;
    @Positive
    @Max(255) @Min(0)
    private int velocidade;
}
