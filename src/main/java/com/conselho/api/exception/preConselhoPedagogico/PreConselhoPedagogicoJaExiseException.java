package com.conselho.api.exception.preConselhoPedagogico;

public class PreConselhoPedagogicoJaExiseException extends RuntimeException{

    public PreConselhoPedagogicoJaExiseException() {
        super("Pré-conselho pedagogico já encontrado!");
    }
}
