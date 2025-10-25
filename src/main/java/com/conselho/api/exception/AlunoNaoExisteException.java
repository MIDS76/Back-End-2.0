package com.conselho.api.exception;

public class AlunoNaoExisteException extends RuntimeException{

    public AlunoNaoExisteException(){
        super("O aluno não existe!");
    }
}
