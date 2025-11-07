package com.conselho.api.exception.conselhoAlunoFeedback;

public class ConselhoAlunoFeedbackExisteException extends RuntimeException{
    public ConselhoAlunoFeedbackExisteException(){
        super("Conselho turma feedback existe.");
    }
}
