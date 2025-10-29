package com.conselho.api.exception.conselho;

public class ConselhoNaoExiste extends RuntimeException {
    public ConselhoNaoExiste (){
        super("Conselho não existe!");
    }
}
