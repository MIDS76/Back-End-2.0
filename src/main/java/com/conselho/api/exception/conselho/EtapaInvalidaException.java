package com.conselho.api.exception.conselho;

public class EtapaInvalidaException extends RuntimeException {
    public EtapaInvalidaException(String etapa) {
        super("Etapa informada é inválida: " + etapa);
    }
}
