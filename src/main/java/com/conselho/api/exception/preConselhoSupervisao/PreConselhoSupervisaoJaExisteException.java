package com.conselho.api.exception.preConselhoSupervisao;

public class PreConselhoSupervisaoJaExisteException extends RuntimeException {
    public PreConselhoSupervisaoJaExisteException() {
        super("Já existe um pré-conselho supervisão registrado.");
    }
}
