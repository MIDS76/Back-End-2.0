package com.conselho.api.serviceTesteUnitario;

import com.conselho.api.dto.mapper.entity.AlunoMapper;
import com.conselho.api.dto.request.entity.AlunoRequestDTO;
import com.conselho.api.dto.response.entity.AlunoResponseDTO;
import com.conselho.api.exception.aluno.AlunoNaoExisteException;

import com.conselho.api.model.entity.Aluno;
import com.conselho.api.model.usuario.UsuarioRole;
import com.conselho.api.repository.entity.AlunoRepository;
import com.conselho.api.repository.entity.UsuarioRepository;
import com.conselho.api.service.entity.AlunoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

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
        Long id = 1L;
        when(usuarioRepository.findById(id))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.buscarAlunoPorId(id);
        });
        assertEquals("Aluno não encontrado!", exception.getMessage());

        verify(usuarioRepository, times(1)).findById(id);
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
    void deletarAluno_DeveLancarExcecao_QuandoNaoExiste() {
        Long id = 1L;
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AlunoNaoExisteException.class, () -> service.deletarAluno(id));

        verify(repository, never()).deleteById(anyLong());
    }
    @Test
    void deletarAluno_DeveDeletarComSucesso() {
        Long id = 1L;
        Aluno aluno = new Aluno("Hellen", "hellen@gmail.com", "1231", "1313");
        aluno.setId(id);
        aluno.setRole(UsuarioRole.ALUNO);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(aluno));

        service.deletarAluno(id);

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void isRepresentante_DeveRetornarTrue_QuandoAlunoEhRepresentante() {
        Long id = 1L;
        when(repository.existsByIdAndRepresentanteTrue(id)).thenReturn(true);

        boolean resultado = service.isRepresentante(id);

        assertTrue(resultado);
        verify(repository, times(1)).existsByIdAndRepresentanteTrue(id);
    }

    @Test
    void isRepresentante_DeveRetornarFalse_QuandoAlunoNaoEhRepresentante() {
        Long id = 1L;
        when(repository.existsByIdAndRepresentanteTrue(id)).thenReturn(false);

        boolean resultado = service.isRepresentante(id);

        assertFalse(resultado);
        verify(repository, times(1)).existsByIdAndRepresentanteTrue(id);
    }
}
