package com.conselho.api.exception.atualizacao;

public class SenhaNaoFornecidaException extends RuntimeException{
    public SenhaNaoFornecidaException(){
        super("Campo de senha não fornecido!");
    }
}
