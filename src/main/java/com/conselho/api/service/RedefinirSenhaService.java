package com.conselho.api.service;

import com.conselho.api.dto.request.ConfirmarRedefinicaoRequestDTO;
import com.conselho.api.dto.request.RedefinirSenhaRequestDTO;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.repository.entity.UsuarioRepository;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RedefinirSenhaService {

    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;

    public void solicitarRedefinicao(RedefinirSenhaRequestDTO requestDTO) {
        Usuario usuario = usuarioRepository.findByEmail(requestDTO.email());

        if (usuario != null) {
            String link = "http://localhost:3000/alterarSenha?email=" + usuario.getEmail();

            try {
                emailService.sendPasswordResetEmail(usuario.getEmail(), link);
            } catch (MessagingException e) {
                System.out.println("Erro ao conectar no gmail!");
                e.printStackTrace();
            }
        } else {
            System.out.println("Verifique se o email '" + requestDTO.email() + "' está correto na tabela usuarios.");
        }
    }

    public void confirmarRedefinicao(ConfirmarRedefinicaoRequestDTO dados) {
        Usuario usuario = usuarioRepository.findByEmail(dados.email());
        if (usuario != null) {
            String senhaCriptografada = new BCryptPasswordEncoder().encode(dados.novaSenha());
            usuario.setSenha(senhaCriptografada);
            if (usuario.isPrimeiroAcesso()) {
                usuario.setPrimeiroAcesso(false);
            }
            usuarioRepository.save(usuario);
            System.out.println("SENHA ALTERADA COM SUCESSO PARA: " + dados.email());
        } else {
            throw new RuntimeException("Usuário não encontrado.");
        }
    }
}