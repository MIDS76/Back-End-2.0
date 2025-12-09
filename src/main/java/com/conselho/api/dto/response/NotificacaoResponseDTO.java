package com.conselho.api.dto.response;

public record NotificacaoResponseDTO(
        Long id,
        String titulo,
        String mensagem,
        boolean lido,
        java.time.LocalDateTime criadoEm
) {
}