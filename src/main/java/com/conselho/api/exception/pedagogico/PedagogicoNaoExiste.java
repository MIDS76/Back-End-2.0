package com.conselho.api.exception.pedagogico;

public class PedagogicoNaoExiste extends RuntimeException{
    public PedagogicoNaoExiste () {
        super("O pedagógico não existe.");
    }
}