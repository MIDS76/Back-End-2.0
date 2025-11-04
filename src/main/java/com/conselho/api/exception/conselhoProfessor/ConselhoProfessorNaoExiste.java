package com.conselho.api.exception.conselhoProfessor;

public class ConselhoProfessorNaoExiste extends RuntimeException {
    public ConselhoProfessorNaoExiste(){
        super("Conselho Professor não existe.");
    }
}
