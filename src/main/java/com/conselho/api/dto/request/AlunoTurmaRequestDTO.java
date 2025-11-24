package com.conselho.api.dto.request;

import com.conselho.api.model.Turma;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public record AlunoTurmaRequestDTO(

        @NotNull(message = "O Id da turma é obrigatório!")
        Long idTurma,

        @NotEmpty(message = "A lista de alunos não pode estar vazia!")
        List<Long> idsAlunos
) {
}
