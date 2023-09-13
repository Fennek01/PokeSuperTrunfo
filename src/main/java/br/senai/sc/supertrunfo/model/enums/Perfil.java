package br.senai.sc.supertrunfo.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Perfil implements GrantedAuthority {

        JOGADOR, ADMIN;

        @Override
        public java.lang.String getAuthority() {
            return this.name();
        }
}
