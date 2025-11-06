package com.conselho.api.dto.response;

public record PreConselhoPedagogicoResponseDTO(
        Long id,
        Long idPreConselho,
        String pontosPositivos,
        String pontosMelhoria,
        String sugestoes
) {
}
