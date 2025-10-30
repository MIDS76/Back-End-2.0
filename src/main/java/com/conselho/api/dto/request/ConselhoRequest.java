package com.conselho.api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Valid
public record ConselhoRequest(
        @NotNull(message = "O id deve ser informado.")
        @Positive(message = "O id não pode ser negativo.")
        Long idTurma,

        @FutureOrPresent(message = "A data de início deve ser hoje ou depois")
        LocalDate dataInicio,

        @Future(message = "A data de fim deve ser uma data futura")
        LocalDate dataFim,

        @NotNull(message = "O id deve ser informado.")
        @Positive(message = "O id não pode ser negativo.")
        Long idRepresentante1,

        @NotNull(message = "O id deve ser informado.")
        @Positive(message = "O id não pode ser negativo.")
        Long idRepresentante2,

        @NotNull(message = "O id deve ser informado.")
        @Positive(message = "O id não pode ser negativo.")
        Long idPedagogico,

        @NotBlank(message = "A etapa do conselho é obrigatório!")
        @NotNull(message = "A etapa deve ser informado.")
        String etapas
) {
}
