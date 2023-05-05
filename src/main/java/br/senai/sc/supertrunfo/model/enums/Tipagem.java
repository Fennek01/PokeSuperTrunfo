package br.senai.sc.supertrunfo.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Tipagem {

    Nenhum("Nenhum"), Fogo("Fogo"), Agua("Água"), Planta("Planta"), Eletrico("Elétrico"), Normal("Normal"), Voador("Voador"), Lutador("Lutador"), Venenoso("Venenoso"), Terra("Terra"), Pedra("Pedra"), Psiquico("Psíquico"), Gelo("Gelo"), Dragao("Dragão"), Fantasma("Fantasma"), Sombrio("Sombrio"), Aco("Aço"), Fada("Fada"), Inseto("Inseto");

    final String tipagem;

}


