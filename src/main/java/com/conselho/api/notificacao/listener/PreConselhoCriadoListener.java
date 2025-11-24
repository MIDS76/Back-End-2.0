package com.conselho.api.notificacao.listener;

import com.conselho.api.dto.mapper.NotificacaoMapper;
import com.conselho.api.dto.response.NotificacaoResponseDTO;
import com.conselho.api.model.Notificacao;
import com.conselho.api.notificacao.event.PreConselhoCriadoEvent;
import com.conselho.api.service.notificacao.NotificacaoCriarService;
import com.conselho.api.service.notificacao.RealTimeNotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Map;

@Component
@AllArgsConstructor
public class PreConselhoCriadoListener {

    private final NotificacaoCriarService criador;
    private final RealTimeNotificationService realtime;
    private final NotificacaoMapper mapper;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onPreConselhoCriado(PreConselhoCriadoEvent event) {
        Map<String, Object> dados = Map.of(
                "preConselhoId", event.getPreConselhoId()
        );

        // aqui vou enviar para representantes da turma quando o pre conselho for criado
        Notificacao notif1 = criador.enviar(
                "PRE_CONSELHO_CRIADO",
                event.getIdRepresentante1(),
                dados
        );

        Notificacao notif2 = criador.enviar(
                "PRE_CONSELHO_CRIADO",
                event.getIdRepresentante2(),
                dados
        );

        NotificacaoResponseDTO response1 = mapper.paraResposta(notif1);
        NotificacaoResponseDTO response2 = mapper.paraResposta(notif2);

        realtime.enviarParaUsuario(
                event.getIdRepresentante1(),
                response1
        );

        realtime.enviarParaUsuario(
                event.getIdRepresentante2(),
                response2
        );
    }
}
