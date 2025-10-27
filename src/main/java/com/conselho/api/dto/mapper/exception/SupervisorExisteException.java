package com.conselho.api.dto.mapper.exception;

public class SupervisorExisteException extends RuntimeException {

    public SupervisorExisteException() {
        super("Supervisor já encontrado!");
    }
}

