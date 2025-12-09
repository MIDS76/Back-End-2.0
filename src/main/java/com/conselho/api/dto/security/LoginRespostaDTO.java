package com.conselho.api.dto.security;

public record LoginRespostaDTO (
        Long id,
        String nome,
        String email,
        String role,
        String token,
        boolean primeiroAcesso){
}
