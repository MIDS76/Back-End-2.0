package com.conselho.api.exception.conselho;

public class ConselhoJaExiste extends RuntimeException {
    public ConselhoJaExiste(){
        super("Conselho já existe!");
    }
}
