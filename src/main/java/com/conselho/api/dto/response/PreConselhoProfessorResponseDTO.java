package com.conselho.api.dto.response;

public record PreConselhoProfessorResponseDTO(

        Long id,
        Long idPreConselho,
        Long idUnidadeCurricular,
        String nomeUc,
        Long idProfessor,
        String nomeProfessor,
        String pontosPositivos,
        String pontosMelhoria,
        String sugestao
) {
}
