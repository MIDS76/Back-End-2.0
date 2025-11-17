package com.conselho.api.service;

import com.conselho.api.model.TokenRedefinicaoSenha;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.repository.entity.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RedefinirSenhaService {

    private UsuarioRepository usuarioRepository;
    private EmailService emailService;
    private TokenRepository tokenRepository;


    public void redefinirSenha(String email) {
        UserDetails usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new RuntimeException("Email não cadastrado!");
        }


        String token = generateResetToken();
        LocalDateTime expiracaoToken = LocalDateTime.now().plusMinutes(30);

        TokenRedefinicaoSenha resetToken = new TokenRedefinicaoSenha(usuario, token);
        tokenRepository.save(resetToken);

        String linkRedefinicao = "http://localhost:3000/redefinir-senha:token=" + token;
        emailService.sendPasswordResetEmail(usuario.getEmail(), linkRedefinicao);

    }
    private String generateResetToken() {
        return UUID.randomUUID().toString();
    }
}
