package com.conselho.api.dto.request.preConselho;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

@Validated
public record PreConselhoProfessorRequestDTO(

        @NotNull(message = "O id deve ser informado.")
        @Positive(message = "O id deve ser positivo")
        Long idPreConselho,

        @NotNull(message = "O id deve ser informado.")
        @Positive(message = "O id deve ser positivo")
        Long idUnidadeCurricular,

        @NotNull(message = "O id deve ser informado.")
        @Positive(message = "O id deve ser positivo")
        Long idProfessor,

        @NotBlank(message = "Os pontos fortes são obrigatórios")
        @NotNull(message = "Os pontos fortes devem ser informado!")
        String pontosPositivos,

        @NotBlank(message = "As oportunidades de melhoria são obrigatórias")
        @NotNull(message = "As oportunidades de melhoria devem ser informado!")
        String pontoMelhoria,

        @NotBlank(message = "A sugestão é obrigatória")
        @NotNull(message = "A sugestão deve ser informado!")
        String sugestoes
) {
}
