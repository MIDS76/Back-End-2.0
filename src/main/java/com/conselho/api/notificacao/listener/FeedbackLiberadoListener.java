package com.conselho.api.notificacao.listener;

import com.conselho.api.dto.mapper.NotificacaoMapper;
import com.conselho.api.dto.response.NotificacaoResponseDTO;
import com.conselho.api.model.Notificacao;
import com.conselho.api.notificacao.event.FeedbackLiberadoEvent;
import com.conselho.api.service.notificacao.NotificacaoCriarService;
import com.conselho.api.service.notificacao.RealTimeNotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Map;

@Component
@AllArgsConstructor
public class FeedbackLiberadoListener {
    private final NotificacaoCriarService criador;
    private final RealTimeNotificationService realtime;
    private final NotificacaoMapper mapper;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onFeedbackLiberado(FeedbackLiberadoEvent event) {
        Map<String, Object> dados = Map.of(
                "conselhoId", event.getConselhoId()
        );

        Notificacao notif1 = criador.enviar(
                "FEEDBACK_LIBERADO",
                event.getAlunoId(),
                dados
        );

        NotificacaoResponseDTO response = mapper.paraResposta(notif1);

        realtime.enviarParaUsuario(
                event.getAlunoId(),
                response
        );
    }
}
