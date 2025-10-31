package com.conselho.api.exception.ConselhoAluno;

import org.springframework.data.jpa.repository.JpaRepository;

public class ConselhoAlunoNaoExiste extends RuntimeException {
    public ConselhoAlunoNaoExiste (){
        super("Conselho não existe!");
    }
}
