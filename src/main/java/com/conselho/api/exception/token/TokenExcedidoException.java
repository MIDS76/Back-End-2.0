package com.conselho.api.exception.token;

public class TokenExcedidoException extends RuntimeException{

    public TokenExcedidoException() {
        super("Token excedido!");
    }
}
