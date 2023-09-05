package br.senai.sc.supertrunfo.security;

public class CookieNotFound extends RuntimeException {

    public CookieNotFound(String message) {
        super("O cookie com o nome " + message + " não foi encontrado!");
    }

}

