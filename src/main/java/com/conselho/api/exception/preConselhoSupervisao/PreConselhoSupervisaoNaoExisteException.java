package com.conselho.api.exception.preConselhoSupervisao;

public class PreConselhoSupervisaoNaoExisteException extends RuntimeException {
    public PreConselhoSupervisaoNaoExisteException(){
        super("Pré-conselho supervisão não encontrado. ");
    }
}
