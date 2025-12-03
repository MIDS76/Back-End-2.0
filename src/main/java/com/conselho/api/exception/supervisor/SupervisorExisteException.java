package com.conselho.api.exception.supervisor;

public class SupervisorExisteException extends RuntimeException {

    public SupervisorExisteException() {
        super("Supervisor já encontrado!");
    }
}

