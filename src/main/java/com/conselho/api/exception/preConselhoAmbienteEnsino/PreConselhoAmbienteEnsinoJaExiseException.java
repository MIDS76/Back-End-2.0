package com.conselho.api.exception.preConselhoAmbienteEnsino;

public class PreConselhoAmbienteEnsinoJaExiseException extends RuntimeException{
    public PreConselhoAmbienteEnsinoJaExiseException (){
        super("Pré-conselho ambiente ensino já encontrado!");
    }
}
