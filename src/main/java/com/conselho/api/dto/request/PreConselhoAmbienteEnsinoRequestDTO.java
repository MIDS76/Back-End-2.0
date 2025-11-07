package com.conselho.api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Valid
public record PreConselhoAmbienteEnsinoRequestDTO(
        @NotNull(message = "O id do pre-conselho ambiente ensino deve ser informado.")
        @Positive(message = "O id do pre-conselho ambiente ensino não pode ser negativo.")
        Long idPreConselho,

        @NotBlank(message = "Os pontos positivos do ambiente ensino é obrigatória")
        @NotNull(message = "Os pontos positivos do ambiente ensino deve ser informada!")
        String pontosPositivos,

        @NotBlank(message = "Os pontos melhoria do ambiente ensino é obrigatória")
        @NotNull(message = "Os pontos melhoria do ambiente ensino deve ser informada!")
        String pontosMelhoria,

        @NotBlank(message = "As sugestoes do ambiente ensino é obrigatória")
        @NotNull(message = "As sugestoes do ambiente ensino deve ser informada!")
        String sugestoes
) {
}
