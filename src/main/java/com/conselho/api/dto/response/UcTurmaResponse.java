package com.conselho.api.dto.response;

public record UcTurmaResponse(
        Long id,
        Long idConselho,
        Long idProfessor,
        String nomeProfessor,

        Long idUnidadeCurricular,
        String nomeUnidadeCurricular
) {
}
