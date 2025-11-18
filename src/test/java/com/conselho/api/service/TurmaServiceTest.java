package com.conselho.api.service;

import com.conselho.api.dto.mapper.TurmaMapper;
import com.conselho.api.dto.request.TurmaRequestDTO;
import com.conselho.api.dto.response.TurmaResponseDTO;
import com.conselho.api.exception.turma.TurmaNaoExisteException;
import com.conselho.api.model.Turma;
import com.conselho.api.repository.TurmaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TurmaServiceTest {

    @Mock
    private TurmaMapper mapper;
    @Mock
    private TurmaRepository repository;
    @Mock
    private Map<Long, List<Long>> turmaMap;

    @InjectMocks
    private TurmaService turmaService;

    @Test
    void deveCriarTurma_ComSucesso() {
        TurmaRequestDTO request = new TurmaRequestDTO("teste", "teste", LocalDate.of(2025,11,13), LocalDate.of(2025, 11, 20));
        Turma turma = new Turma();
        turma.setId(1L);

        TurmaResponseDTO response = new TurmaResponseDTO(1L, "Turma X", "teste", LocalDate.of(2025,11,13), LocalDate.of(2025, 11, 20) );

        when(mapper.paraEntidade(request)).thenReturn(turma);
        when(repository.save(turma)).thenReturn(turma);
        when(mapper.paraResposta(turma)).thenReturn(response);

        TurmaResponseDTO result = turmaService.criarTurma(request);

        assertEquals(response, result);
        verify(repository, times(1)).save(turma);
    }

    @Test
    void deveBuscarTodasAsTurmas_ComSucesso() {
        Turma t1 = new Turma();
        t1.setId(1L);
        Turma t2 = new Turma();
        t2.setId(2L);

        when(repository.findAll()).thenReturn(List.of(t1, t2));
        when(mapper.paraResposta(t1)).thenReturn(new TurmaResponseDTO(1L, "T1", "teste", LocalDate.of(2025,11,13), LocalDate.of(2025, 11, 20) ));
        when(mapper.paraResposta(t2)).thenReturn(new TurmaResponseDTO(2L, "T2", "teste",  LocalDate.of(2025,11,13), LocalDate.of(2025, 11, 20)));

        var result = turmaService.listarTurmas();

        assertEquals(2, result.size());
        assertEquals("T1", result.get(0).nome());
        assertEquals("T2", result.get(1).nome());
    }

    @Test
    void deveBuscarTurmaPorId_ComSucesso() {
        Turma turma = new Turma();
        turma.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(turma));
        TurmaResponseDTO response = new TurmaResponseDTO(1L, "Turma Y", "teste",  LocalDate.of(2025,11,13), LocalDate.of(2025, 11, 20));
        when(mapper.paraResposta(turma)).thenReturn(response);

        var result = turmaService.buscarTurmaPorId(1L);

        assertEquals(response, result);
    }

    @Test
    void deveLancarErro_QuandoTurmaNaoExiste_AoBuscarPorId() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(TurmaNaoExisteException.class, () -> turmaService.buscarTurmaPorId(1L));
    }

    @Test
    void deveAtualizarTurma_ComSucesso() {
        TurmaRequestDTO request = new TurmaRequestDTO("Nova Turma", "teste",  LocalDate.of(2025,11,13), LocalDate.of(2025, 11, 20));
        Turma turma = new Turma();
        turma.setId(1L);
        Turma atualizado = new Turma();
        atualizado.setId(1L);

        TurmaResponseDTO response = new TurmaResponseDTO(1L, "Mids", "teste",  LocalDate.of(2025,11,13), LocalDate.of(2025, 11, 20));

        when(repository.findById(1L)).thenReturn(Optional.of(turma));
        when(mapper.paraUpdate(request, turma)).thenReturn(atualizado);
        when(repository.save(atualizado)).thenReturn(atualizado);
        when(mapper.paraResposta(atualizado)).thenReturn(response);

        var result = turmaService.atualizarTurma(1L, request);

        assertEquals(response, result);
        verify(repository, times(1)).save(atualizado);
    }

    @Test
    void deveLancarErro_QuandoTurmaNaoExiste_AoAtualizar() {
        TurmaRequestDTO request = new TurmaRequestDTO("Nova Turma", "teste",  LocalDate.of(2025,11,13), LocalDate.of(2025, 11, 20));
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TurmaNaoExisteException.class, () -> turmaService.atualizarTurma(1L, request));
        verify(repository, never()).save(any());
    }

    @Test
    void deveDeletarTurma_ComSucesso() {
        Turma turma = new Turma();
        when(repository.findById(1L)).thenReturn(Optional.of(turma));

        turmaService.deletarTurma(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deveLancarErro_QuandoTurmaNaoExiste_AoDeletar() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TurmaNaoExisteException.class, () -> turmaService.deletarTurma(1L));
        verify(repository, never()).deleteById(any());
    }
}
