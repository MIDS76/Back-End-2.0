package com.conselho.api.exception.preConselhoAmbienteEnsino;

public class PreConselhoAmbienteEnsinoNaoExisteException extends RuntimeException{
    public PreConselhoAmbienteEnsinoNaoExisteException() {
        super("Pré Conselho ambiente ensino não encontrado.");
    }
}
