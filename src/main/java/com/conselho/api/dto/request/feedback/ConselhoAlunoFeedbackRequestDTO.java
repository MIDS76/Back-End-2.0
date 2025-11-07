package com.conselho.api.dto.request.feedback;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ConselhoAlunoFeedbackRequestDTO (
        @NotNull(message = "O id deve ser informado.")
        @Positive(message = "O id não pode ser negativo.")
        Long idConselho,

        @NotNull(message = "O id deve ser informado.")
        @Positive(message = "O id não pode ser negativo.")
        Long idPedagogico,

        @NotNull(message = "O id deve ser informado.")
        @Positive(message = "O id não pode ser negativo.")
        Long idAluno,

        @NotBlank(message = "Os pontos positivos do feedback aluno é obrigatório!")
        String pontosPositivos,

        @NotBlank(message = "Os pontos de melhoria do feedback aluno é obrigatório!")
        String pontosMelhoria,

        @NotBlank(message = "A sugestão do feedback aluno é obrigatório!")
        String sugestao
){
}
