package com.conselho.api.dto.request;

public record AtualizarPreConselhoProfessorRequestDTO(
        String pontosPositivos,
        String oportunidadeMelhoria,
        String sugestoes
) {
}
