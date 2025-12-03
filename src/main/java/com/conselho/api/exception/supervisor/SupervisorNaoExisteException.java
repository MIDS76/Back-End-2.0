package com.conselho.api.exception.supervisor;

public class SupervisorNaoExisteException extends RuntimeException {

    public SupervisorNaoExisteException() {
        super("Supervisor não encontrado!");
    }
}