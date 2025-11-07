package com.conselho.api.dto.response.preConselho;

public record PreConselhoPedagogicoResponseDTO(
        Long id,
        Long idPreConselho,
        String pontosPositivos,
        String pontosMelhoria,
        String sugestoes
) {
}
