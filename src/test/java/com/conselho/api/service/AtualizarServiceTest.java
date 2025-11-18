package com.conselho.api.serviceTesteUnitario;

import com.conselho.api.model.entity.Aluno;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.repository.entity.AlunoRepository;
import com.conselho.api.repository.entity.UsuarioRepository;
import com.conselho.api.service.AtualizarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AtualizarServiceTest {
    @InjectMocks
    AtualizarService service;

    @Mock
    private UsuarioRepository repository;
    @Mock
    private AlunoRepository alunoRepository;

    @Test
    void atualizarSenha() {
        Usuario usuario = new Usuario();
        usuario.setPrimeiroAcesso(true);

        Map<String, String> campoAtualizacao = new HashMap<>();
        campoAtualizacao.put("senha", "novaSenha123");

        when(repository.findById(1L)).thenReturn(Optional.of(usuario));
        when(repository.save(usuario)).thenReturn(usuario);

        service.atualizarSenha(1L, campoAtualizacao);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        assertTrue(encoder.matches("novaSenha123", usuario.getSenha()));

        assertFalse(usuario.isPrimeiroAcesso());

        verify(repository).save(usuario);
    }

    @Test
    void atualizarRepresentante() {
        Aluno aluno = new Aluno();

        Map<String, Boolean> campoAtualizacao = new HashMap<>();
        campoAtualizacao.put("representante", true);

        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(alunoRepository.save(aluno)).thenReturn(aluno);

        service.atualizarRepresentante(1L, campoAtualizacao);

        verify(alunoRepository).save(aluno);
    }
}