package com.conselho.api.serviceTesteUnitario;

import com.conselho.api.dto.mapper.ConselhoMapper;
import com.conselho.api.dto.request.ConselhoRequestDTO;
import com.conselho.api.dto.response.ConselhoResponseDTO;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.pedagogico.PedagogicoNaoExiste;
import com.conselho.api.exception.representante.RepresentanteNaoExiste;
import com.conselho.api.exception.turma.TurmaNaoExisteException;
import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.model.entity.Aluno;
import com.conselho.api.model.entity.Pedagogico;
import com.conselho.api.model.entity.Turma;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.TurmaRepository;
import com.conselho.api.repository.entity.AlunoRepository;
import com.conselho.api.repository.entity.PedagogicoRepository;
import com.conselho.api.service.ConselhoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConselhoServiceTest {

    @Mock
    private ConselhoMapper mapper;
    @Mock
    private ConselhoRepository conselhoRepository;
    @Mock
    private TurmaRepository turmaRepository;
    @Mock
    private AlunoRepository alunoRepository;
    @Mock
    private PedagogicoRepository pedagogicoRepository;

    @InjectMocks
    private ConselhoService conselhoService;

    @Test
    void deveCriarConselho_ComSucesso() {
        LocalDate dataInicio = LocalDate.of(2023, 12, 25);
        LocalDate dataFim = LocalDate.of(2024, 12, 25);

        ConselhoRequestDTO request = new ConselhoRequestDTO(1L, dataInicio, dataFim, 2L, 3L, 4L);

        Conselho conselho = new Conselho();
        Conselho salvo = new Conselho();

        ConselhoResponseDTO response = new ConselhoResponseDTO(1L, 1L, "Turma X", 2L, "Representante 1", 3L, "Representante 2", 4L, "Pedagógico", dataInicio, dataFim, "Etapa 1");

        when(mapper.paraEntidade(request)).thenReturn(conselho);
        when(turmaRepository.findById(1L)).thenReturn(Optional.of(new Turma()));
        when(alunoRepository.findById(2L)).thenReturn(Optional.of(new Aluno()));
        when(alunoRepository.findById(3L)).thenReturn(Optional.of(new Aluno()));
        when(pedagogicoRepository.findById(4L)).thenReturn(Optional.of(new Pedagogico()));
        when(conselhoRepository.save(conselho)).thenReturn(salvo);
        when(mapper.paraResposta(salvo)).thenReturn(response);

        ConselhoResponseDTO result = conselhoService.criarConselho(request);

        assertEquals(response, result);
        verify(conselhoRepository, times(1)).save(conselho);
    }

    @Test
    void deveLancarErro_QuandoTurmaNaoExiste() {
        ConselhoRequestDTO request = new ConselhoRequestDTO(1L, LocalDate.of(2023, 12, 25), LocalDate.of(2024, 12, 25), 2L, 3L, 4L);
        Conselho conselho = new Conselho();

        when(mapper.paraEntidade(request)).thenReturn(conselho);
        when(turmaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TurmaNaoExisteException.class, () -> conselhoService.criarConselho(request));
        verify(conselhoRepository, never()).save(any());
    }

    @Test
    void deveLancarErro_QuandoRepresentanteNaoExiste() {
        ConselhoRequestDTO request = new ConselhoRequestDTO(1L, LocalDate.of(2023, 12, 25), LocalDate.of(2024, 12, 25), 2L, 3L, 4L);
        Conselho conselho = new Conselho();

        when(mapper.paraEntidade(request)).thenReturn(conselho);
        when(turmaRepository.findById(1L)).thenReturn(Optional.of(new Turma()));
        when(alunoRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RepresentanteNaoExiste.class, () -> conselhoService.criarConselho(request));
    }

    @Test
    void deveLancarErro_QuandoPedagogicoNaoExiste() {
        ConselhoRequestDTO request = new ConselhoRequestDTO(1L, LocalDate.of(2023, 12, 25), LocalDate.of(2024, 12, 25), 2L, 3L, 4L);
        Conselho conselho = new Conselho();

        when(mapper.paraEntidade(request)).thenReturn(conselho);
        when(turmaRepository.findById(1L)).thenReturn(Optional.of(new Turma()));
        when(alunoRepository.findById(2L)).thenReturn(Optional.of(new Aluno()));
        when(alunoRepository.findById(3L)).thenReturn(Optional.of(new Aluno()));
        when(pedagogicoRepository.findById(4L)).thenReturn(Optional.empty());

        assertThrows(PedagogicoNaoExiste.class, () -> conselhoService.criarConselho(request));
    }

    @Test
    void deveDeletarConselho_ComSucesso() {
        when(conselhoRepository.existsById(1L)).thenReturn(true);

        conselhoService.deletarConselho(1L);

        verify(conselhoRepository, times(1)).deleteById(1L);
    }

    @Test
    void deveLancarErro_QuandoConselhoNaoExiste_aoDeletar() {
        when(conselhoRepository.existsById(1L)).thenReturn(false);
        assertThrows(ConselhoNaoExiste.class, () -> conselhoService.deletarConselho(1L));
        verify(conselhoRepository, never()).deleteById(any());
    }

    @Test
    void deveBuscarTodosConselhos() {
        LocalDate dataInicio = LocalDate.of(2023, 12, 25);
        LocalDate dataFim = LocalDate.of(2024, 12, 25);

        Conselho conselho = new Conselho();
        ConselhoResponseDTO response = new ConselhoResponseDTO(1L, 1L, "Turma X", 2L, "Representante 1", 3L, "Representante 2", 4L, "Pedagógico", dataInicio, dataFim, "Etapa 1");

        when(conselhoRepository.findAll()).thenReturn(List.of(conselho));
        when(mapper.paraResposta(conselho)).thenReturn(response);

        var result = conselhoService.listarConselhos();

        assertEquals(1, result.size());
        assertEquals(response, result.get(0));

        verify(conselhoRepository, times(1)).findAll();
    }

    @Test
    void deveBuscarConselhoPorId() {
        LocalDate dataInicio = LocalDate.of(2023, 12, 25);
        LocalDate dataFim = LocalDate.of(2024, 12, 25);
        Conselho conselho = new Conselho();
        ConselhoResponseDTO response = new ConselhoResponseDTO(1L, 1L, "Turma X", 2L, "Representante 1", 3L, "Representante 2", 4L, "Pedagógico", dataInicio, dataFim, "Etapa 1");

        when(conselhoRepository.findById(1L)).thenReturn(Optional.of(conselho));
        when(mapper.paraResposta(conselho)).thenReturn(response);

        var result = conselhoService.buscarConselhoPorId(1L);

        assertEquals(response, result);

        verify(conselhoRepository, times(1)).findById(1L);
    }

    @Test
    void deveLancarErro_QuandoConselhoNaoExiste_AoBuscarPorId() {
        when(conselhoRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ConselhoNaoExiste.class, () -> conselhoService.buscarConselhoPorId(1L));

        verify(conselhoRepository, times(1))
                .findById(1L);
    }

    @Test
    void deveAtualizarConselho_ComSucesso() {
        ConselhoRequestDTO request = new ConselhoRequestDTO(1L, LocalDate.of(2023, 12, 25), LocalDate.of(2024, 12, 25), 2L, 3L, 4L);
        Conselho existente = new Conselho();
        Conselho atualizado = new Conselho();
        LocalDate dataInicio = LocalDate.of(2023, 12, 25);
        LocalDate dataFim = LocalDate.of(2024, 12, 25);

        ConselhoResponseDTO response = new ConselhoResponseDTO(1L, 1L, "Turma Y", 2L, "Representante 1", 3L, "Representante 2", 4L, "Pedagógico", dataInicio, dataFim, "Etapa 1");

        when(conselhoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(mapper.verificarUpdate(request, existente)).thenReturn(atualizado);
        when(conselhoRepository.save(atualizado)).thenReturn(atualizado);
        when(mapper.paraResposta(atualizado)).thenReturn(response);

        var result = conselhoService.atualizarConselho(1L, request);

        assertEquals(response, result);

        verify(conselhoRepository, times(1)).save(atualizado);
    }

    @Test
    void deveLancarErro_QuandoConselhoNaoExiste_AoAtualizar() {
        ConselhoRequestDTO request = new ConselhoRequestDTO(1L, LocalDate.of(2023, 12, 25), LocalDate.of(2024, 12, 25), 2L, 3L, 4L);

        when(conselhoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ConselhoNaoExiste.class, () -> conselhoService.atualizarConselho(1L, request));

        verify(conselhoRepository, times(1)).findById(1L);
    }
}