package com.conselho.api.exception.preConselho;

public class PreConselhoExisteException extends RuntimeException {
    public PreConselhoExisteException (){
        super("Já existe um pré-conselho registrado para este conselho.");
    }
}
