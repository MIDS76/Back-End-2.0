package com.conselho.api.exception.conselhoTurmaFeedback;

public class ConselhoTurmaFeedbackNaoExisteException extends RuntimeException {
    public ConselhoTurmaFeedbackNaoExisteException (){
        super("Turma feedback não encontrado.");
    }
}
