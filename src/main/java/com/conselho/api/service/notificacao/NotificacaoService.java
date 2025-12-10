package com.conselho.api.service.notificacao;

import com.conselho.api.dto.mapper.NotificacaoMapper;
import com.conselho.api.dto.response.NotificacaoResponseDTO;
import com.conselho.api.exception.notificacao.NotificacaoNaoExisteException;
import com.conselho.api.model.notificacao.Notificacao;
import com.conselho.api.model.notificacao.TipoNotificacao;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.repository.NotificacaoRepository;
import com.conselho.api.repository.entity.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class NotificacaoService {
    private final NotificacaoRepository repository;
    private final NotificacaoMapper mapper;
    private final UsuarioRepository usuarioRepository;

    public List<Notificacao> criarNotificacao (String titulo, String mensagem, List<Long> usuarioIds, TipoNotificacao tipoNotificacao, Long referencia){
        if (usuarioIds == null || usuarioIds.isEmpty()) {
            throw new IllegalArgumentException("A lista de usuários não pode estar vazia.");
        }

        List<Notificacao> notificacoesCriadas = new ArrayList<>();

        for (Long usuarioId : usuarioIds) {
            Usuario usuario = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

            Notificacao notificacao = new Notificacao();
            notificacao.setTitulo(titulo);
            notificacao.setMensagem(mensagem);
            notificacao.setUsuario(usuario);
            notificacao.setLido(false);
            notificacao.setCriadoEm(LocalDateTime.now());
            notificacao.setTipo(tipoNotificacao);
            notificacao.setReferenciaId(referencia);

            // Salva a notificação e adiciona à lista
            notificacoesCriadas.add(repository.save(notificacao));
        }
        return notificacoesCriadas;
    }

    public List<NotificacaoResponseDTO> buscarTodasPorUsuario (Long usuarioId) {
        List<Notificacao> notificacoes = repository.findByUsuarioId(usuarioId);

        return notificacoes.stream()
                .map(mapper::paraResposta)
                .toList();
    }

    public List<NotificacaoResponseDTO> listarNaoLidas(Long usuarioId) {
        List<Notificacao> notificacoes = repository
                .findByUsuarioIdAndLidoFalseOrderByCriadoEmDesc(usuarioId);

        return notificacoes.stream()
                .map(mapper::paraResposta)
                .toList();
    }


    public void deletar (Long usuarioId) {
        List<Notificacao> notificacoes = repository.findByUsuarioId(usuarioId);

        if (notificacoes != null && !notificacoes.isEmpty()){
            repository.deleteAll(notificacoes);
        } else {
            throw new RuntimeException("Não há notificações para este usuário.");
        }
    }

    public NotificacaoResponseDTO marcarLida (Long notificacaoId) {
        Notificacao notificacao = repository.findById(notificacaoId)
                .orElseThrow(NotificacaoNaoExisteException::new);

        notificacao.setLido(true);

        Notificacao notificacaoSaved = repository.save(notificacao);

        return mapper.paraResposta(notificacaoSaved);
    }
}
