package com.conselho.api.service.notificacao;

import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RealTimeNotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    public void enviarParaUsuario(Long userId, Object payload) {
        messagingTemplate.convertAndSend("/topic/notificacoes" + userId, payload);
    }
}
