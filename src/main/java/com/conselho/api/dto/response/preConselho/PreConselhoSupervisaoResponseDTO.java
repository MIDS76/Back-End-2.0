package com.conselho.api.dto.response.preConselho;

public record PreConselhoSupervisaoResponseDTO(
        Long id,
        Long idPreConselho,
        String pontosPostivos,
        String pontosMelhoria,
        String sugestoes
) {
}
