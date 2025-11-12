package com.conselho.api.dto.response;

public record UcProfessorResponseDTO(
        Long id,
        Long idConselho,
        Long idProfessor,
        String nomeProfessor,

        Long idUnidadeCurricular,
        String nomeUnidadeCurricular
) {
}
