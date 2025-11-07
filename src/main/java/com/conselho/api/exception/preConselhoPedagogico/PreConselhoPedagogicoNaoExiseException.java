package com.conselho.api.exception.preConselhoPedagogico;

public class PreConselhoPedagogicoNaoExiseException extends RuntimeException{
    public PreConselhoPedagogicoNaoExiseException(){
        super("Pré Conselho pedagogico não encontrado.");

    }
}
