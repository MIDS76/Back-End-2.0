package com.conselho.api.service.notificacao;

import com.conselho.api.dto.mapper.NotificacaoMapper;
import com.conselho.api.dto.response.NotificacaoResponseDTO;
import com.conselho.api.exception.notificacao.NotificacaoNaoExisteException;
import com.conselho.api.model.Notificacao;
import com.conselho.api.repository.NotificacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class NotificacaoService {
    private final NotificacaoRepository repository;
    private final NotificacaoMapper mapper;
    private final RealTimeNotificationService realtime;

    public List<NotificacaoResponseDTO> listarNaoLidas(Long usuarioId){
        List<Notificacao> notificacoes = repository.findByUsuarioIdAndLidaFalseOrderByCriadaEmDesc(usuarioId);

        return notificacoes.stream()
                .map(mapper::paraResposta)
                .toList();
    }

    public List<NotificacaoResponseDTO> buscarTodas (Long usuarioId) {
        List<Notificacao> notificacoes = repository.findByUsuarioIdOrderByCriadaEmDesc(usuarioId);

        return notificacoes.stream()
                .map(mapper::paraResposta)
                .toList();
    }

    public NotificacaoResponseDTO marcarComoLida (Long id) {
        Notificacao n = repository.findById(id)
                .orElseThrow(NotificacaoNaoExisteException::new);

        if (!n.isLido()){
            n.setLido(true);
        }

        Notificacao salvo = repository.save(n);

        realtime.enviarParaUsuario(
                salvo.getUsuario().getId(),
                mapper.paraResposta(salvo)
        );

        return mapper.paraResposta(salvo);
    }
}
