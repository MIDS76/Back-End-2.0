package com.conselho.api.exception.professor;

public class ProfessorExisteException extends RuntimeException{

    public ProfessorExisteException(){
        super("Professor já encontrado!");
    }
}

