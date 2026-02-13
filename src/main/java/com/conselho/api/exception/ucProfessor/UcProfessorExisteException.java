package com.conselho.api.exception.ucProfessor;

public class UcProfessorExisteException extends RuntimeException{
    public UcProfessorExisteException(){
        super("UcProfessor já existe!");
    }
}
