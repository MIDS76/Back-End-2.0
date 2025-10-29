package com.conselho.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UnidadeCurricularRequest(
        @NotBlank(message = "O nome da unidade curricular é obrigatório")
        @NotNull(message = "O nome da unidade curricular deve ser informado!")
        String nome
) {
}
