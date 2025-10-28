package com.conselho.api.dto.mapper.exception;

public class ProfessorNaoExisteException extends RuntimeException{

    public ProfessorNaoExisteException(){
        super("Professor não encontrado!");
    }
}
