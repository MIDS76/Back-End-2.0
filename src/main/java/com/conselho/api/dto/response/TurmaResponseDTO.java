package com.conselho.api.dto.response;

import java.util.List;

public record TurmaResponseDTO(
        Long id,
        String nome,
        String curso,
        List<String> nomesAlunos
){
}
