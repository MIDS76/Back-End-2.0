package com.conselho.api.exception.professor;

public class ProfessorNaoExisteException extends RuntimeException{

    public ProfessorNaoExisteException(){
        super("Professor não encontrado!");
    }
}
