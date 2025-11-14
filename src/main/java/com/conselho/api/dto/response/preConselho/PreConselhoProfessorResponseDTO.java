package com.conselho.api.dto.response.preConselho;

public record PreConselhoProfessorResponseDTO(

        Long id,
        Long idPreConselho,
        Long idUnidadeCurricular,
        String nomeUc,
        Long idProfessor,
        String nomeProfessor,
        String pontosPositivos,
        String pontoMelhoria,
        String sugestoes
) {
}
