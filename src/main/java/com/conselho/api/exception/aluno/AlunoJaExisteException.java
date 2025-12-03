package com.conselho.api.exception.aluno;

public class AlunoJaExisteException extends RuntimeException{
    public AlunoJaExisteException(){
        super("O aluno já existe!");
    }
}
