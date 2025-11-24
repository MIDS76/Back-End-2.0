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
        List<Notificacao> notificacoes = repository.findByUsuarioIdAndLidoFalseOrderByCriadoEmDesc(usuarioId);

        return notificacoes.stream()
                .map(mapper::paraResposta)
                .toList();
    }

    public List<NotificacaoResponseDTO> buscarTodas (Long usuarioId) {
        List<Notificacao> notificacoes = repository.findByUsuarioIdOrderByCriadoEmDesc(usuarioId);

        return notificacoes.stream()
                .map(mapper::paraResposta)
                .toList();
    }

    public NotificacaoResponseDTO marcarComoLida (Long id) {
        Notificacao n = repository.findById(id)
                .orElseThrow(NotificacaoNaoExisteException::new);

        if (!n.isLido()){
            n.setLido(true);
            repository.save(n);
        }

        realtime.enviarParaUsuario(
                n.getUsuario().getId(),
                mapper.paraResposta(n)
        );

        return mapper.paraResposta(n);
    }

    public void deletarNotificacao(Long id) {
        if (!repository.existsById(id)){
            throw new NotificacaoNaoExisteException();
        }
        repository.deleteById(id);
    }
}
