package com.conselho.api.dto.security;

public record LoginRespostaDTO (
        String email,
        String role,
        String token,
        boolean primeiroAcesso){
}
