package com.conselho.api.dto.response;

public record PreConselhoAmbienteEnsinoResponseDTO(
        Long id,
        Long idPreConselho,
        String pontosPositivos,
        String pontosMelhoria,
        String sugestoes
) {
}
