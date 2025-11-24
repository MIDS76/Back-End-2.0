package com.conselho.api.service.entity;

import com.conselho.api.dto.mapper.entity.AlunoMapper;
import com.conselho.api.dto.request.entity.AlunoRequestDTO;
import com.conselho.api.dto.response.entity.AlunoResponseDTO;
import com.conselho.api.exception.aluno.AlunoNaoExisteException;

import com.conselho.api.model.entity.Aluno;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.model.usuario.UsuarioRole;
import com.conselho.api.repository.entity.AlunoRepository;
import com.conselho.api.repository.entity.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlunoServiceTest {

    @InjectMocks
    private AlunoService service;

    @Mock
    private AlunoRepository repository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AlunoMapper mapper;

    @Test
    void listarAluno_DeveRetornarListaDeAlunos() {
        Aluno aluno1 = new Aluno("Vitor", "vitinho@gmail.com", "123", "1231");
        aluno1.setId(1L);
        aluno1.setRole(UsuarioRole.ALUNO);

        Aluno aluno2 = new Aluno("Hellen", "hellen@gmail.com", "121", "1222");
        aluno2.setId(2L);
        aluno2.setRole(UsuarioRole.ALUNO);

        when(usuarioRepository.findAll()).thenReturn(List.of(aluno1, aluno2));

        List<AlunoResponseDTO> result = service.listarAlunos();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Vitor", result.get(0).nome());
        assertEquals("Hellen", result.get(1).nome());
    }

    @Test
    void buscarAlunoPorId_DeveLancarExcecao_QuandoAlunoNaoExiste() {
        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(AlunoNaoExisteException.class, () -> service.deletarAluno(1L));

        verify(repository, never()).deleteById(any());
    }
    @Test
    void buscarAlunoPorId_DeveRetornarAluno_QuandoExistir() {
        Long id = 1L;
        Aluno aluno = new Aluno("Hellen", "hellen@gmail.com", "123", "1235");
        aluno.setId(id);
        aluno.setRole(UsuarioRole.ALUNO);

        AlunoResponseDTO response = new AlunoResponseDTO(id, "Hellen", "hellen@gmail.com", "123", false);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(aluno));
        when(mapper.paraResposta(aluno)).thenReturn(response);

        AlunoResponseDTO result = service.buscarAlunoPorId(id);

        assertNotNull(result);
        assertEquals("Hellen", result.nome());
        assertEquals("hellen@gmail.com", result.email());
        assertEquals("123", result.senha());
        assertFalse(result.representante());
    }

    @Test
    void atualizarAluno_DeveAtualizarComSucesso() {
        Long id = 1L;
        AlunoRequestDTO request = new AlunoRequestDTO("Hellen", "hellen@gmail.com", "123");
        Aluno alunoExiste = new Aluno("Hellen", "hellen@gmail.com", "123", "1231");
        alunoExiste.setId(id);
        alunoExiste.setRole(UsuarioRole.ALUNO);

        when(repository.findById(id)).thenReturn(Optional.of(alunoExiste));
        when(usuarioRepository.findByEmail(request.email())).thenReturn(null);
        when(repository.save(alunoExiste)).thenReturn(alunoExiste);

        service.atualizarAluno(id, request);

        verify(repository, times(1)).save(alunoExiste);
    }


    @Test
    void deletarAluno_DeveDeletarComSucesso() {
        Long id = 1L;
        Aluno aluno = new Aluno("Hellen", "hellen@gmail.com", "1231", "1313");
        aluno.setId(id);
        aluno.setRole(UsuarioRole.ALUNO);

        when(repository.findById(id)).thenReturn(Optional.of(aluno));

        service.deletarAluno(id);

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void buscarAtividade_DeveRetornarListaDeAlunosAtivos() {
        Aluno aluno1 = new Aluno();
        aluno1.setId(1L);
        aluno1.setNome("Aluno 1");
        aluno1.setEmail("aluno1@example.com");
        aluno1.setSenha("senha1");
        aluno1.setRepresentante(true);

        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Aluno 2");
        aluno2.setEmail("aluno2@example.com");
        aluno2.setSenha("senha2");
        aluno2.setRepresentante(false);

        when(usuarioRepository.findByRoleAndAtivo(UsuarioRole.ALUNO, true))
                .thenReturn(Arrays.asList(aluno1, aluno2));

        List<AlunoResponseDTO> resultado = service.buscarAtividade(true);

        assertEquals(2, resultado.size());
        assertEquals(1L, resultado.get(0).id());
        assertEquals("Aluno 1", resultado.get(0).nome());
        assertEquals("aluno1@example.com", resultado.get(0).email());
        assertEquals(true, resultado.get(0).representante());

        assertEquals(2L, resultado.get(1).id());
        assertEquals("Aluno 2", resultado.get(1).nome());
        assertEquals("aluno2@example.com", resultado.get(1).email());
        assertEquals(false, resultado.get(1).representante());
    }

    @Test
    void buscarAtividade_DeveRetornarListaDeAlunosInativos() {
        Aluno aluno1 = new Aluno();
        aluno1.setId(1L);
        aluno1.setNome("Aluno 1");
        aluno1.setEmail("aluno1@example.com");
        aluno1.setSenha("senha1");
        aluno1.setRepresentante(true);

        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Aluno 2");
        aluno2.setEmail("aluno2@example.com");
        aluno2.setSenha("senha2");
        aluno2.setRepresentante(false);

        when(usuarioRepository.findByRoleAndAtivo(UsuarioRole.ALUNO, false))
                .thenReturn(Arrays.asList(aluno1, aluno2));

        List<AlunoResponseDTO> resultado = service.buscarAtividade(false);

        assertEquals(2, resultado.size());
        assertEquals(1L, resultado.get(0).id());
        assertEquals("Aluno 1", resultado.get(0).nome());
        assertEquals("aluno1@example.com", resultado.get(0).email());
        assertEquals(true, resultado.get(0).representante());

        assertEquals(2L, resultado.get(1).id());
        assertEquals("Aluno 2", resultado.get(1).nome());
        assertEquals("aluno2@example.com", resultado.get(1).email());
        assertEquals(false, resultado.get(1).representante());
    }

    @Test
    void buscarAtividade_DeveRetornarListaVaziaParaStatusInexistente() {
        when(usuarioRepository.findByRoleAndAtivo(UsuarioRole.ALUNO, true))
                .thenReturn(Arrays.asList());

        List<AlunoResponseDTO> resultado = service.buscarAtividade(true);

        assertEquals(0, resultado.size());
    }

    @Test
    void buscarAtividade_DeveRetornarListaVaziaParaAlunosInativos() {
        when(usuarioRepository.findByRoleAndAtivo(UsuarioRole.ALUNO, false))
                .thenReturn(Arrays.asList());

        List<AlunoResponseDTO> resultado = service.buscarAtividade(false);

        assertEquals(0, resultado.size());
    }

    @Test
    void ordenarAlunosOrdemAlfabetica_DeveOrdenarEmOrdemCrescente_AZ() {
        Aluno aluno1 = new Aluno("Ana", "Ana", "ana@example.com", "senha", true); // Aluno 1
        Aluno aluno2 = new Aluno("Beatriz", "Carlos", "carlos@example.com", "senha", false); // Aluno 2
        Aluno aluno3 = new Aluno("Carlos", "Beatriz", "beatriz@example.com", "senha", true); // Aluno 3

        List<Usuario> usuariosMock = new ArrayList<>();
        usuariosMock.add(aluno1);
        usuariosMock.add(aluno2);
        usuariosMock.add(aluno3);

        when(usuarioRepository.findAll()).thenReturn(usuariosMock);

        List<AlunoResponseDTO> alunosOrdenados = service.ordenarAlunosOrdemAlfabetica("A-Z");

        assertEquals("Ana", alunosOrdenados.get(0).nome());
        assertEquals("Beatriz", alunosOrdenados.get(1).nome());
        assertEquals("Carlos", alunosOrdenados.get(2).nome());

        verify(usuarioRepository, times(1)).findAll();
    }
    @Test
    void ordenarAlunosOrdemAlfabetica_DeveOrdenarEmOrdemDecrescente_ZA() {
        Aluno aluno1 = new Aluno("Ana", "ana@example.com", "senha", "senha", true);
        Aluno aluno2 = new Aluno("Carlos", "carlos@example.com", "senha", "senha", false);
        Aluno aluno3 = new Aluno("Beatriz", "beatriz@example.com", "senha", "senha", true);

        List<Usuario> usuariosMock = new ArrayList<>();
        usuariosMock.add(aluno1);
        usuariosMock.add(aluno2);
        usuariosMock.add(aluno3);

        when(usuarioRepository.findAll()).thenReturn(usuariosMock);

        List<AlunoResponseDTO> alunosOrdenados = service.ordenarAlunosOrdemAlfabetica("Z-A");

        assertEquals("Carlos", alunosOrdenados.get(0).nome());
        assertEquals("Beatriz", alunosOrdenados.get(1).nome());
        assertEquals("Ana", alunosOrdenados.get(2).nome());

        verify(usuarioRepository, times(1)).findAll();
    }

}
