package com.conselho.api.exception.turma;

public class TurmaJaExisteException extends RuntimeException{

    public TurmaJaExisteException(){
        super("Turma já existe!");
    }
}
