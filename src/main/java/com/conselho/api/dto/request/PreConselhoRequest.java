package com.conselho.api.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record PreConselhoRequest(

        @NotNull(message = "O id deve ser informado.")
        @Positive(message = "O id não pode ser negativo.")
        Long idConselho,

        @FutureOrPresent(message = "A data de início deve ser hoje ou depois")
        LocalDate dataInicio,

        @Future(message = "A data de fim deve ser uma data futura")
        LocalDate dataFim
) {
}
