package com.conselho.api.dto.response;

public record PreConselhoSupervisaoResponseDTO(
        Long id,
        Long idPreConselho,
        String pontosPostivos,
        String oportunidadeMelhoria,
        String sugestoes
) {
}
