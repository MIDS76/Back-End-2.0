package com.conselho.api.dto.response;

import java.util.List;

public record AlunoTurmaResponseDTO(
        Long id,
        String nomeTurma,
        List<String> nomeAluno ,
        boolean ativo

) {
}
