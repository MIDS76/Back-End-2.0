package com.conselho.api.exception.pedagogico;

public class PedagogicoJaExiste extends RuntimeException{
    public PedagogicoJaExiste (){
        super("O nome do pedagógico já existe.");
    }
}