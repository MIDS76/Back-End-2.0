package com.conselho.api.dto.response;

public record UcTurmaResponseDTO(
        Long id,
        Long idConselho,
        Long idProfessor,
        String nomeProfessor,

        Long idUnidadeCurricular,
        String nomeUnidadeCurricular
) {
}
