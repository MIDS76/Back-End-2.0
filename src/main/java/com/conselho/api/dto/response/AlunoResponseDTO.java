package com.conselho.api.dto.response;

public record AlunoResponseDTO(

        Long id,
        String nome,
        String email,
        String senha,
        boolean representante


) {
}
