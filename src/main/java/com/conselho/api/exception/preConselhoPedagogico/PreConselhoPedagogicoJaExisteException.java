package com.conselho.api.exception.preConselhoPedagogico;

public class PreConselhoPedagogicoJaExisteException extends RuntimeException{

    public PreConselhoPedagogicoJaExisteException() {
        super("Pré-conselho pedagogico já encontrado!");
    }
}
