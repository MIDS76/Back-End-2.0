package com.conselho.api.exception.preConselhoAmbienteEnsino;

public class PreConselhoAmbienteEnsinoJaExisteException extends RuntimeException{
    public PreConselhoAmbienteEnsinoJaExisteException(){
        super("Pré-conselho ambiente ensino já encontrado!");
    }
}
