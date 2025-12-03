package com.conselho.api.dto.response.feedback;

public record ConselhoTurmaFeedbackResponseDTO (
        Long id,
        Long idCOnselho,
        Long idPedagogico,
        String nomePedagogico,
        String pontosPositivos,
        String pontosMelhoria,
        String sugestao
){
}
