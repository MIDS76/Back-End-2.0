package com.conselho.api.exception.turma;

public class TurmaJaExiste extends RuntimeException{

    public TurmaJaExiste(){
        super("Turma já existe!");
    }
}
