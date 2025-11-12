package com.conselho.api.dto.response.entity;

public record AlunoResponseDTO(

        Long id,
        String nome,
        String email,
        String senha,
        boolean representante


) {
}
