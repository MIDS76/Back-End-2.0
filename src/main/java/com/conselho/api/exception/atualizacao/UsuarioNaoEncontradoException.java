package com.conselho.api.exception.atualizacao;

public class UsuarioNaoEncontradoException extends RuntimeException{
    public UsuarioNaoEncontradoException(){
        super("Usuário não encontrado!");
    }
}
