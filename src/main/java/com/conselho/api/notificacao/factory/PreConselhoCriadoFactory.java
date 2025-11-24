package com.conselho.api.notificacao.factory;

import com.conselho.api.model.Notificacao;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.repository.entity.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@AllArgsConstructor
@Component("PRE_CONSELHO_CRIADO")
public class PreConselhoCriadoFactory implements NotificacaoFactory {
    private final UsuarioRepository usuarioRepository;
    @Override
    public Notificacao enviar(Long usuarioId, Map<String, Object> dados) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(()-> new RuntimeException("Usuário não encontrado."));

        return Notificacao.builder()
                .titulo("Pré-conselho disponível")
                .mensagem("Há um pré-conselho disponível para preenchimento.")
                .usuario(usuario)
                .lido(false)
                .criadoEm(Instant.now())
                .build();
    }
}
