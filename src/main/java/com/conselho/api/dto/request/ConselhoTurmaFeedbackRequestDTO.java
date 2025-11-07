package com.conselho.api.dto.request;

import jakarta.validation.constraints.*;

public record ConselhoTurmaFeedbackRequestDTO (
        @NotNull(message = "O id deve ser informado.")
        @Positive(message = "O id não pode ser negativo.")
        Long idConselho,

        @NotNull(message = "O id deve ser informado.")
        @Positive(message = "O id não pode ser negativo.")
        Long idPedagogico,

        @NotBlank(message = "Os pontos positivos do feedback turma é obrigatório!")
        String pontosPositivos,

        @NotBlank(message = "Os pontos de melhoria do feedback turma é obrigatório!")
        String pontosMelhoria,

        @NotBlank(message = "A sugestão do feedback turma é obrigatório!")
        String sugestao
){
}
