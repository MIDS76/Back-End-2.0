package com.conselho.api.exception.unidadeCurricular;

public class UnidadeCurricularNaoExisteException extends RuntimeException{

    public UnidadeCurricularNaoExisteException () {
        super("A unidade curricular não existe.");
    }
}
