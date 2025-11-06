package com.conselho.api.exception.preConselhoProfessor;

public class PreConselhoProfessorNaoExisteException extends RuntimeException {

    public PreConselhoProfessorNaoExisteException() {
        super("Pré Conselho Professor não encontrado!");
    }
}
