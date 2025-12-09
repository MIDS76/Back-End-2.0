package com.conselho.api.dto.response;

import com.conselho.api.model.notificacao.TipoNotificacao;

import java.time.LocalDateTime;

public record NotificacaoResponseDTO(
        Long id,
        String titulo,
        String mensagem,
        boolean lido,
        LocalDateTime criadoEm,
        TipoNotificacao tipoNotificacao
) {
}