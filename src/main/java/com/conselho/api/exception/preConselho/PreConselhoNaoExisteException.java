package com.conselho.api.exception.preConselho;

public class PreConselhoNaoExisteException extends RuntimeException {
    public PreConselhoNaoExisteException () {
        super("Pré Conselho não encontrado.");
    }
}
