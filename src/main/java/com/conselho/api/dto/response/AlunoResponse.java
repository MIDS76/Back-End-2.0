package com.conselho.api.dto.response;

public record AlunoResponse(

        Long id,
        String nome,
        String email,
        String senha,
        boolean is_representative
) {
}
