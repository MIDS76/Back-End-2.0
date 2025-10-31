package com.conselho.api.exception.unidadeCurricular;

public class UnidadeCurricularExisteException extends RuntimeException{
    public UnidadeCurricularExisteException (){
        super("A Unidade Curricular já existe.");
    }
}

