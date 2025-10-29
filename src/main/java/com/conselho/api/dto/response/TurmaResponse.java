package com.conselho.api.dto.response;

import com.conselho.api.model.aluno.Aluno;

import java.util.List;

public record TurmaResponse (
        Long id,
        String nome,
        String curso,
        List<String> nomesAlunos
){
}
