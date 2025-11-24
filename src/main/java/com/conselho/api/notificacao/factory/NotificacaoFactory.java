package com.conselho.api.notificacao.factory;

import com.conselho.api.model.Notificacao;

import java.util.Map;

public interface NotificacaoFactory {
    Notificacao enviar (Long usuarioId, Map<String, Object> dados);
}
