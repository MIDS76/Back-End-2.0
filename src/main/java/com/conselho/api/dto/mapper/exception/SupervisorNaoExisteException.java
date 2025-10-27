package com.conselho.api.dto.mapper.exception;

public class SupervisorNaoExisteException extends RuntimeException {

    public SupervisorNaoExisteException() {
        super("Supervisor não encontrado!");
    }
}