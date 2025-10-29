package com.conselho.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record TurmaRequest(
        @NotBlank(message = "O nome da turma é obrigatório!")
        @NotNull(message = "O nome deve ser informado.")
        String nome,
        @NotBlank(message = "O nome do curso é obrigatório!")
        @NotNull(message = "O nome deve ser informado.")
        String curso,
        List<Long> idAlunos
) {
}
