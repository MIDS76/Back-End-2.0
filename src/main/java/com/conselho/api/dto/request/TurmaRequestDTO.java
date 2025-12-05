package com.conselho.api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

@Valid
public record TurmaRequestDTO(
        @NotBlank(message = "O nome da turma é obrigatório!")
        @NotNull(message = "O nome deve ser informado.")
        String nome,
        @NotBlank(message = "O nome do curso é obrigatório!")
        @NotNull(message = "O nome deve ser informado.")
        String curso,

        @NotNull(message = "A data de inicio deve ser informada")
        LocalDate dataInicio,

        @NotNull(message = "A data final deve ser informada")
        @Future(message = "A data deve ser futura")
        LocalDate dataFinal
) {
}
