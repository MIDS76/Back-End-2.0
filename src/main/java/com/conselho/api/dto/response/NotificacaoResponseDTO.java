package com.conselho.api.dto.response;

import java.time.Instant;

public record NotificacaoResponseDTO(
        Long id,
        String titulo,
        String mensagem,
        boolean lido,
        Instant criadoEm
) {
}