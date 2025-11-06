package com.conselho.api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Valid
public record PreConselhoPedagogicoRequestDTO(
        @NotNull(message = "O id do pre-conselho pedagogico deve ser informado.")
        @Positive(message = "O id do pre-conselho pedagogico não pode ser negativo.")
        Long idPreConselho,

        @NotBlank(message = "Os pontos positivos do pedagogico é obrigatória")
        @NotNull(message = "Os pontos positivos do pedagogico deve ser informada!")
        String pontosPositivos,

        @NotBlank(message = "Os pontos melhoria do pedagogico é obrigatória")
        @NotNull(message = "Os pontos melhoria do pedagogico deve ser informada!")
        String pontosMelhoria,

        @NotBlank(message = "As sugestoes do pedagogico é obrigatória")
        @NotNull(message = "As sugestoes do pedagogico deve ser informada!")
        String sugestoes
) {
}
