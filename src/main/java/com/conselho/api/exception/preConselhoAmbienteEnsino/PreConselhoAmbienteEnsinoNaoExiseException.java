package com.conselho.api.exception.preConselhoAmbienteEnsino;

public class PreConselhoAmbienteEnsinoNaoExiseException extends RuntimeException{
    public PreConselhoAmbienteEnsinoNaoExiseException() {
        super("Pré Conselho ambiente ensino não encontrado.");
    }
}
