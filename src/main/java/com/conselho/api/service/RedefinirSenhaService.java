package com.conselho.api.service;

import com.conselho.api.dto.request.RedefinirSenhaRequestDTO;
import com.conselho.api.exception.token.EmailNaoExisteException;
import com.conselho.api.exception.token.TokenExcedidoException;
import com.conselho.api.model.TokenRedefinicaoSenha;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.repository.TokenRepository;
import com.conselho.api.repository.entity.UsuarioRepository;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RedefinirSenhaService {

    private UsuarioRepository usuarioRepository;
    private EmailService emailService;
    private TokenRepository tokenRepository;

    public void redefinirSenha(RedefinirSenhaRequestDTO requestDTO) {
        Usuario usuario = usuarioRepository.findByEmail(requestDTO.email());
        if (usuario == null) {
            throw new EmailNaoExisteException();
        }

        String token = generateResetToken();
        LocalDateTime expiracaoToken = LocalDateTime.now().plusMinutes(30);

        TokenRedefinicaoSenha resetToken = new TokenRedefinicaoSenha(token, expiracaoToken, usuario);
        tokenRepository.save(resetToken);

        if (LocalDateTime.now().isAfter(resetToken.getTempoExpiracao())) {
            throw new TokenExcedidoException();
        }

        String linkRedefinicao = "http://localhost:3000/redefinir-senha?token=" + token;
        try {
            emailService.sendPasswordResetEmail(usuario.getEmail(), linkRedefinicao);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar o e-mail de redefinição de senha.", e);
        }
    }
    private String generateResetToken() {
        return UUID.randomUUID().toString();
    }
}
