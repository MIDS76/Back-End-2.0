package com.conselho.api.exception.turma;

public class TurmaNaoExiste extends RuntimeException {

    public TurmaNaoExiste(){
        super("Turma não existe!");
    }
}
