package com.conselho.api.dto.response.feedback;

public record ConselhoAlunoFeedbackResponseDTO (
    Long id,
    Long idCOnselho,
    Long idPedagogico,
    String nomePedagogico,
    Long idAluno,
    String nomeAluno,
    String pontosPositivos,
    String pontosMelhoria,
    String sugestao
){
}
