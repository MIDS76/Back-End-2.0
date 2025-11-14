package com.conselho.api.exception.preConselhoPedagogico;

public class PreConselhoPedagogicoNaoExisteException extends RuntimeException{
    public PreConselhoPedagogicoNaoExisteException(){
        super("Pré Conselho pedagogico não encontrado.");

    }
}
