package com.conselho.api.dto.mapper;

import com.conselho.api.dto.response.NotificacaoResponseDTO;
import com.conselho.api.model.Notificacao;

import java.time.Instant;

public class NotificacaoMapper {
    public NotificacaoResponseDTO paraResposta (Notificacao notificacao){
        return new NotificacaoResponseDTO(
                notificacao.getId(),
                notificacao.getTitulo(),
                notificacao.getMensagem(),
                notificacao.isLido(),
                notificacao.getCriadoEm()
        );
    }
}
