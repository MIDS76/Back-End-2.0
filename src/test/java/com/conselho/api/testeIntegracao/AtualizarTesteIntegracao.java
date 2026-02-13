package com.conselho.api.testeIntegracao;

import com.conselho.api.model.entity.Aluno;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.model.usuario.UsuarioRole;
import com.conselho.api.repository.entity.AlunoRepository;
import com.conselho.api.repository.entity.UsuarioRepository;
import com.conselho.api.service.AtualizarService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestConstructor;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class AtualizarTesteIntegracao {
    private final UsuarioRepository usuarioRepository;
    private final AlunoRepository alunoRepository;
    private final AtualizarService atualizarService;

    private Usuario usuario;
    private Aluno aluno;

    public AtualizarTesteIntegracao(
        UsuarioRepository usuarioRepository,
        AlunoRepository alunoRepository,
        AtualizarService atualizarService
    ){
        this.usuarioRepository = usuarioRepository;
        this.alunoRepository = alunoRepository;
        this.atualizarService = atualizarService;
    }

    @BeforeEach
    void setup() {
        usuario = new Usuario();
        usuario.setNome("Hellen");
        usuario.setEmail("hellenzita@gmail.com");
        usuario.setSenha(new BCryptPasswordEncoder().encode("primeiroAcesso"));
        usuario.setRole(UsuarioRole.ALUNO);
        usuario.setPrimeiroAcesso(true);

        usuarioRepository.save(usuario);

        aluno = new Aluno();
        aluno.setMatricula("1334");
        aluno.setNome("Julia");
        aluno.setEmail("jolia@gmail.com");
        aluno.setSenha("primeiroAcesso");
        aluno.setRepresentante(false);
        aluno.setRole(UsuarioRole.ALUNO);
        alunoRepository.save(aluno);
    }
    @Test
    void deveAtualizarSenha() {
        Map<String, String> atualizarCampo = new HashMap<>();
        atualizarCampo.put("senha", "jolia123");

        atualizarService.atualizarSenha(usuario.getId(), atualizarCampo);

        Usuario usuarioAtualizado = usuarioRepository.findById(usuario.getId()).orElseThrow();
        assertTrue(new BCryptPasswordEncoder().matches("jolia123", usuarioAtualizado.getSenha()));
        assertFalse(usuarioAtualizado.isPrimeiroAcesso());
    }

    @Test
    void deveAtualizarRepresentante() {
        Map<String, Boolean> campoRepre = new HashMap<>();
        campoRepre.put("representante", true);

        atualizarService.atualizarRepresentante(aluno.getId(), campoRepre);

        Aluno alunoAtualizado = alunoRepository.findById(aluno.getId()).orElseThrow();
        assertTrue(alunoAtualizado.isRepresentante());
    }
    @Test
    void deveAtualizarSenhaCampoNaoFornecido() {
        Map<String, String> atualizadoCampo = new HashMap<>();

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                atualizarService.atualizarSenha(usuario.getId(), atualizadoCampo));
        assertEquals("Campo de senha não fornecido!", exception.getMessage());
    }

    @Test
    void deveAtualizarRepresentanteCampoNaoFornecido() {
        Map<String, Boolean> campoAtualizado = new HashMap<>();

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                atualizarService.atualizarRepresentante(aluno.getId(), campoAtualizado));
        assertEquals("Campo de representante não fornecido!", exception.getMessage());
    }
}




