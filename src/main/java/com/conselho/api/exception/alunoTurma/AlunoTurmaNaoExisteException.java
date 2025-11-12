package com.conselho.api.exception.alunoTurma;

public class AlunoTurmaNaoExisteException extends RuntimeException{

    public AlunoTurmaNaoExisteException(){
        super("O aluno não pertence a essa turma");
    }
}
