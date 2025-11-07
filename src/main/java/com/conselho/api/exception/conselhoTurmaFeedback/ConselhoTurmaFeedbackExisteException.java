package com.conselho.api.exception.conselhoTurmaFeedback;

public class ConselhoTurmaFeedbackExisteException extends RuntimeException{
    public ConselhoTurmaFeedbackExisteException (){
        super("Conselho Turma feedback já existe.");
    }
}
