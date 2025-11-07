package com.conselho.api.exception.weg;

public class WegNaoExisteException extends RuntimeException {
    public WegNaoExisteException() {
        super("Usuário weg não encontrado.");
    }
}
