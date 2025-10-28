package com.conselho.api.dto.response;

public record PedagogicoResponse (
        Long id,
        String nome,
        String email,
        String senha
) {
}