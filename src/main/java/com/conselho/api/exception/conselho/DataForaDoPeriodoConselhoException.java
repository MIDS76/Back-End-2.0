package com.conselho.api.exception.conselho;

public class DataForaDoPeriodoConselhoException extends RuntimeException{
    public DataForaDoPeriodoConselhoException () {
        super("A data de início e/ou fim do pré-conselho deve estar dentro do período do conselho.");
    }
}
