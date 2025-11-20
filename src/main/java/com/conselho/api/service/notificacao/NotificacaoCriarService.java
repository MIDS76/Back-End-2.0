package com.conselho.api.service.notificacao;

import com.conselho.api.model.Notificacao;
import com.conselho.api.notificacao.factory.NotificacaoFactory;
import com.conselho.api.repository.NotificacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Map;

@AllArgsConstructor
@Service
public class NotificacaoCriarService {
    private final NotificacaoRepository repository;
    private final Map<String, NotificacaoFactory> factoryMap;

    /**
     * Cria e persiste uma notificação usando a factory registrada pelo nome do tipo.
     *
     * @param tipo nome do bean da factory. Ex: "PRE_CONSELHO_CRIADO"
     * @param usuarioId id do usuário que receberá a notificação
     * @param dados mapa de dados opcionais enviados para a factory
     * @return notificação criada e salva
     */

    public Notificacao enviar (String tipo, Long usuarioId, Map<String, Object> dados){
        NotificacaoFactory factory = factoryMap.get(tipo);
        if (factory == null){
            throw new IllegalArgumentException("Tipo de notificação não suportado: " + tipo);
        }

        Notificacao n = factory.enviar(usuarioId, dados);
        return repository.save(n);
    }
}
