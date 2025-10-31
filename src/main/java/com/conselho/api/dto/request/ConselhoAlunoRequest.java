package com.conselho.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ConselhoAlunoRequest(

        @NotNull(message = "O id do aluno deve ser informado.")
        @Positive(message = "O id do aluno não pode ser negativo.")
        Long idAluno,

        @NotNull(message = "O id da turma deve ser informado.")
        @Positive(message = "O id da turma não pode ser negativo.")
        Long idConselho
){
}
