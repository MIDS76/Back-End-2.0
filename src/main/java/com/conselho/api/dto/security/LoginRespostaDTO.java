package com.conselho.api.dto.security;

public record LoginRespostaDTO (
        Long id,
        String email,
        String role,
        String token,
        boolean primeiroAcesso){
}
