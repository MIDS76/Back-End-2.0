package com.conselho.api.dto.request.preConselho;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

@Validated
public record PreConselhoSupervisaoRequestDTO(

        @NotNull(message = "O id do pré-conselho deve ser informado.")
        @Positive(message = "O id pré-conselho não pode ser negativo.")
        Long idPreConselho,
        @NotBlank(message = "O pontos positivos do pré-conselho supervisão é obrigatório!")
        @NotNull(message = "O pontos positivos deve ser informado.")
        String pontosPositivos,
        @NotBlank(message = "A pontos de melhoria do pré-conselho supervisão é obrigatório!")
        @NotNull(message = "A pontos de melhoria deve ser informado.")
        String pontosMelhoria,
        @NotBlank(message = "As sugestões do pré-conselho supervisão é obrigatório!")
        @NotNull(message = "As sugestões deve ser informado.")
        String sugestoes
) {
}
