package com.conselho.api.dto.response;

public record ProfessorResponse (

        Long id,
        String nome,
        String email,
        String senha
) {
}
