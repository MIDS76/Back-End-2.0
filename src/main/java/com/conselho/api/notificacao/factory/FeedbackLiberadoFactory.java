package com.conselho.api.notificacao.factory;

import com.conselho.api.model.Notificacao;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.repository.entity.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@AllArgsConstructor
@Component("FEEDBACK_LIBERADO")
public class FeedbackLiberadoFactory implements NotificacaoFactory {
    private final UsuarioRepository usuarioRepository;

    @Override
    public Notificacao enviar(Long usuarioId, Map<String, Object> dados) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(()-> new RuntimeException("Usuário não encontrado."));

        return Notificacao.builder()
                .usuario(usuario)
                .titulo("Novo feedback disponível")
                .mensagem("Um novo feedback do conselho está disponível para você.")
                .lido(false)
                .criadoEm(Instant.now())
                .build();
    }
}