package com.conselho.api.exception.usuario;

public class UsuarioNaoExisteExcption extends RuntimeException{
    public UsuarioNaoExisteExcption(){
        super("Usuario nao existe!" +
                "");
    }
}
