package com.conselho.api.notificacao.factory;

import com.conselho.api.model.Notificacao;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.repository.entity.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@AllArgsConstructor
@Component("PRE_CONSELHO_FINALIZADO")
public class PreConselhoFinalizadoFactory implements NotificacaoFactory {
    private final UsuarioRepository usuarioRepository;

    @Override
    public Notificacao enviar(Long usuarioId, Map<String, Object> dados){
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        return Notificacao.builder()
                .titulo("Pré-conselho finalizado")
                .mensagem("O pré-conselho da turma foi finalizado e está disponível para análise")
                .usuario(usuario)
                .lido(false)
                .criadoEm(Instant.now())
                .build();
    }
}