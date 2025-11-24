package com.conselho.api.notificacao.listener;

import com.conselho.api.dto.mapper.NotificacaoMapper;
import com.conselho.api.dto.response.NotificacaoResponseDTO;
import com.conselho.api.model.Notificacao;
import com.conselho.api.notificacao.event.PreConselhoFinalizadoEvent;
import com.conselho.api.service.notificacao.NotificacaoCriarService;
import com.conselho.api.service.notificacao.RealTimeNotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Map;

@Component
@AllArgsConstructor
public class PreConselhoFinalizadoListener {
    private final NotificacaoCriarService criador;
    private final RealTimeNotificationService realtime;
    private final NotificacaoMapper mapper;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onPreConselhoFinalizado(PreConselhoFinalizadoEvent event) {
        Map<String, Object> dados = Map.of(
                "preConselho", event.getPreConselhoId()
        );

        // aqui vou enviar para pedagogico quando o pre conselho for finalizado
        Notificacao n = criador.enviar(
                "PRE_CONSELHO_FINALIZADO",
                event.getPedagogicoId(),
                dados
        );

        NotificacaoResponseDTO response = mapper.paraResposta(n);

        realtime.enviarParaUsuario(
                event.getPedagogicoId(),
                response
        );
    }
}