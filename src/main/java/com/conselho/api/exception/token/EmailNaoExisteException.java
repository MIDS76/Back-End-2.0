package com.conselho.api.exception.token;

public class EmailNaoExisteException extends RuntimeException{

    public EmailNaoExisteException() {
        super("Email não cadastrado!");
    }
}
