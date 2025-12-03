package com.conselho.api.exception.aluno;

public class AlunoNaoExisteException extends RuntimeException{

    public AlunoNaoExisteException(){
        super("O aluno não existe!");
    }
}
