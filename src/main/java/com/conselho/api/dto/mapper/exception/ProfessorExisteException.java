package com.conselho.api.dto.mapper.exception;

public class ProfessorExisteException extends RuntimeException{

    public ProfessorExisteException(){
        super("Professor já encontrado!");
    }
}

