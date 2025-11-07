package com.conselho.api.exception.conselhoAlunoFeedback;

public class ConselhoAlunoFeedbackNaoExisteException extends RuntimeException{
    public ConselhoAlunoFeedbackNaoExisteException(){
        super("Conselho Aluno Feedback não encontrado.");
    }
}
