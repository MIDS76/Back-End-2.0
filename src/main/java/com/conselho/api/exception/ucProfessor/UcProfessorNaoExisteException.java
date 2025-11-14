package com.conselho.api.exception.ucProfessor;

public class UcProfessorNaoExisteException extends RuntimeException{
    public UcProfessorNaoExisteException(){
        super("UcProfessor não existe!");
    }
}
