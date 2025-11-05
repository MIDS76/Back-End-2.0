package com.conselho.api.dto.request;

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
        @NotBlank(message = "O ponto positivo do pré-conselho supervisão é obrigatório!")
        @NotNull(message = "O ponto positivo deve ser informado.")
        String pontoPositivo,
        @NotBlank(message = "A oportunidade de melhoria do pré-conselho supervisão é obrigatório!")
        @NotNull(message = "A oportunidade de melhoria deve ser informado.")
        String oportunidadeMelhoria,
        @NotBlank(message = "As sugestões do pré-conselho supervisão é obrigatório!")
        @NotNull(message = "As sugestões deve ser informado.")
        String sugestoes
) {
}
