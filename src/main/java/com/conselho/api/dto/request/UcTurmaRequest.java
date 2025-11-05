package com.conselho.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UcTurmaRequest(
        @NotNull(message = "O id deve ser informado.")
        @Positive(message = "O id não pode ser negativo.")
        Long idConselho,
        @NotNull(message = "O id deve ser informado.")
        @Positive(message = "O id não pode ser negativo.")
        Long idProfessor,
        @NotNull(message = "O id deve ser informado.")
        @Positive(message = "O id não pode ser negativo.")
        Long idUnidadeCurricular
) {
}
