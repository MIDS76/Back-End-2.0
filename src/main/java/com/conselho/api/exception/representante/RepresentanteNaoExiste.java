package com.conselho.api.exception.representante;

import org.springframework.data.jpa.repository.JpaRepository;

public class RepresentanteNaoExiste extends RuntimeException {
    public RepresentanteNaoExiste(){
        super("O representante não existe.");
    }
}
