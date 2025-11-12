package com.conselho.api.exception.turma;

public class TurmaNaoExisteException extends RuntimeException {

    public TurmaNaoExisteException(){
        super("Turma não existe!");
    }
}
