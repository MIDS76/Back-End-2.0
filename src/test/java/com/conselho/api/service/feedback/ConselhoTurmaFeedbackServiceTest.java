package com.conselho.api.service.feedback;

import com.conselho.api.dto.mapper.feedback.ConselhoTurmaFeedbackMapper;
import com.conselho.api.dto.request.feedback.ConselhoTurmaFeedbackRequestDTO;
import com.conselho.api.dto.response.feedback.ConselhoTurmaFeedbackResponseDTO;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.conselhoAlunoFeedback.ConselhoAlunoFeedbackNaoExisteException;
import com.conselho.api.exception.conselhoTurmaFeedback.ConselhoTurmaFeedbackExisteException;
import com.conselho.api.exception.conselhoTurmaFeedback.ConselhoTurmaFeedbackNaoExisteException;
import com.conselho.api.exception.pedagogico.PedagogicoNaoExiste;
import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.model.entity.Pedagogico;
import com.conselho.api.model.feedback.ConselhoTurmaFeedback;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.entity.PedagogicoRepository;

import com.conselho.api.repository.feedback.ConselhoTurmaFeedbackRepository;
import com.conselho.api.service.feedback.ConselhoTurmaFeedbackService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class ConselhoTurmaFeedbackServiceTest {
    @InjectMocks
    private ConselhoTurmaFeedbackService service;
    @Mock
    private ConselhoTurmaFeedbackMapper mapper;
    @Mock
    private ConselhoTurmaFeedbackRepository repository;
    @Mock
    private PedagogicoRepository pedagogicoRepository;
    @Mock
    private ConselhoRepository conselhoRepository;

    @Test
    void create() {
        ConselhoTurmaFeedbackRequestDTO request = new ConselhoTurmaFeedbackRequestDTO(
                1L,
                1L,
                "teste",
                "teste",
                "teste"
        );
        ConselhoTurmaFeedback alunoFeedback = new ConselhoTurmaFeedback();
        ConselhoTurmaFeedback salvo = new ConselhoTurmaFeedback();
        Pedagogico pedagogico = new Pedagogico();
        Conselho conselho = new Conselho();
        ConselhoTurmaFeedbackResponseDTO response = new ConselhoTurmaFeedbackResponseDTO(1L,
                1L,
                1L,
                "henrique",
                "julia",
                "teste",
                "teste"
        );

        when(mapper.paraEntidade(request)).thenReturn(alunoFeedback);
        when(conselhoRepository.findById(request.idConselho())).thenReturn(Optional.of(conselho));
        when(pedagogicoRepository.findById(request.idPedagogico())).thenReturn(Optional.of(pedagogico));
        when(repository.existsByConselhoId(1L)).thenReturn(false);
        when(repository.save(alunoFeedback)).thenReturn(salvo);
        when(mapper.paraResposta(salvo)).thenReturn(response);

        ConselhoTurmaFeedbackResponseDTO result = service.create(request);

        assertEquals(response, result);

        verify(repository, times(1)).save(alunoFeedback);
    }

    @Test
    void create_ConselhoNaoExiste_DeveLancarExcecao () {
        ConselhoTurmaFeedbackRequestDTO request = new ConselhoTurmaFeedbackRequestDTO(
                99L,
                99L,
                "teste",
                "teste",
                "teste"
        );
        ConselhoTurmaFeedback turmaFeedback = new ConselhoTurmaFeedback();

        when(mapper.paraEntidade(request)).thenReturn(turmaFeedback);
        when(conselhoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ConselhoNaoExiste.class, () -> {
            service.create(request);
        });

        verify(repository, never()).save(any());
    }

    @Test
    void create_PedagogicoNaoExiste_DeveLancarExcecao () {
        ConselhoTurmaFeedbackRequestDTO request = new ConselhoTurmaFeedbackRequestDTO(
                99L,
                99L,
                "teste",
                "teste",
                "teste"
        );
        ConselhoTurmaFeedback turmaFeedback = new ConselhoTurmaFeedback();
        Conselho conselho = new Conselho();

        when(mapper.paraEntidade(request)).thenReturn(turmaFeedback);
        when(conselhoRepository.findById(request.idConselho())).thenReturn(Optional.of(conselho));
        when(pedagogicoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(PedagogicoNaoExiste.class, () -> {
            service.create(request);
        });

        verify(repository, never()).save(any());
    }

    @Test
    void create_AlunofeedbackExiste_deveLancarExcecao () {
        ConselhoTurmaFeedbackRequestDTO request = new ConselhoTurmaFeedbackRequestDTO(
                1L,
                1L,
                "teste",
                "teste",
                "teste"
        );
        ConselhoTurmaFeedback turmaFeedback = new ConselhoTurmaFeedback();
        Conselho conselho = new Conselho();
        Pedagogico pedagogico = new Pedagogico();

        when(mapper.paraEntidade(request)).thenReturn(turmaFeedback);
        when(conselhoRepository.findById(1L)).thenReturn(Optional.of(conselho));
        when(pedagogicoRepository.findById(1L)).thenReturn(Optional.of(pedagogico));
        when(repository.existsByConselhoId(request.idConselho())).thenReturn(true);

        assertThrows(ConselhoTurmaFeedbackExisteException.class, () -> {
            service.create(request);
        });

        verify(repository, never()).save(any());
    }

    @Test
    void buscarTodos() {
        ConselhoTurmaFeedbackResponseDTO response = new ConselhoTurmaFeedbackResponseDTO(
                1L,
                1L,
                1L,
                "henrique",
                "teste",
                "teste",
                "teste"
        );
        ConselhoTurmaFeedback turmaFeedback = new ConselhoTurmaFeedback();

        when(repository.findAll()).thenReturn(List.of(turmaFeedback));
        when(mapper.paraResposta(turmaFeedback)).thenReturn(response);

        List<ConselhoTurmaFeedbackResponseDTO> result = service.buscarTodos();

        assertEquals(1, result.size());
        assertEquals(response, result.get(0));

        verify(repository, times(1)).findAll();
    }

    @Test
    void buscarPorId() {
        ConselhoTurmaFeedback alunoFeedback = new ConselhoTurmaFeedback();
        ConselhoTurmaFeedbackResponseDTO response = new ConselhoTurmaFeedbackResponseDTO(
                1L,
                1L,
                1L,
                "henrique",
                "teste",
                "teste",
                "teste"
        );

        when(repository.findById(1L)).thenReturn(Optional.of(alunoFeedback));
        when(mapper.paraResposta(alunoFeedback)).thenReturn(response);

        ConselhoTurmaFeedbackResponseDTO result = service.buscarPorId(1L);

        assertEquals(response, result);
    }

    @Test
    void buscarPorId_turmaFeedbackNaoExiste_DeveLancarExcecao () {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ConselhoTurmaFeedbackNaoExisteException.class, () -> {
            service.buscarPorId(1L);
        });

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void update() {
        ConselhoTurmaFeedbackRequestDTO request = new ConselhoTurmaFeedbackRequestDTO(
                1L,
                1L,
                "teste",
                "teste",
                "teste"
        );
        ConselhoTurmaFeedback existente = new ConselhoTurmaFeedback();
        ConselhoTurmaFeedback atualizado = new ConselhoTurmaFeedback();
        ConselhoTurmaFeedbackResponseDTO response = new ConselhoTurmaFeedbackResponseDTO(
                1L,
                1L,
                1L,
                "henrique",
                "teste",
                "teste",
                "teste"
        );

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(mapper.verificarUpdate(request, existente)).thenReturn(atualizado);
        when(repository.save(atualizado)).thenReturn(atualizado);
        when(mapper.paraResposta(atualizado)).thenReturn(response);

        ConselhoTurmaFeedbackResponseDTO result = service.update(1L, request);

        assertEquals(response, result);

        verify(repository).findById(1L);
        verify(mapper).verificarUpdate(request, existente);
        verify(repository, times(1)).save(atualizado);
        verify(mapper).paraResposta(atualizado);
    }

    @Test
    void update_turmaFeedbackNaoExiste_DeveLancarExcecao () {
        ConselhoTurmaFeedbackRequestDTO request = new ConselhoTurmaFeedbackRequestDTO(
                1L,
                1L,
                "teste",
                "teste",
                "teste"
        );

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ConselhoTurmaFeedbackNaoExisteException.class, () -> {
            service.update(1L, request);
        });

        verify(repository, never()).save(any());
    }

    @Test
    void delete() {
        when(repository.existsById(1L)).thenReturn(true);
        service.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deveLancarErro_ConselhoTurmaFeedback_aoDeletar() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(ConselhoTurmaFeedbackNaoExisteException.class, () -> service.delete(1L));

        verify(repository, never()).deleteById(any());
    }
}