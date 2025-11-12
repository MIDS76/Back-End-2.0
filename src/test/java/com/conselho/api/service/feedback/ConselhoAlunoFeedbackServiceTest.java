package com.conselho.api.service.feedback;

import com.conselho.api.dto.mapper.feedback.ConselhoAlunoFeedbackMapper;
import com.conselho.api.dto.request.feedback.ConselhoAlunoFeedbackRequestDTO;
import com.conselho.api.dto.response.feedback.ConselhoAlunoFeedbackResponseDTO;
import com.conselho.api.dto.response.feedback.ConselhoTurmaFeedbackResponseDTO;
import com.conselho.api.exception.aluno.AlunoNaoExisteException;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.conselhoAlunoFeedback.ConselhoAlunoFeedbackExisteException;
import com.conselho.api.exception.conselhoAlunoFeedback.ConselhoAlunoFeedbackNaoExisteException;
import com.conselho.api.exception.pedagogico.PedagogicoNaoExiste;
import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.model.entity.Aluno;
import com.conselho.api.model.entity.Pedagogico;
import com.conselho.api.model.feedback.ConselhoAlunoFeedback;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.entity.AlunoRepository;
import com.conselho.api.repository.entity.PedagogicoRepository;
import com.conselho.api.repository.feedback.ConselhoAlunoFeedbackRepository;
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

class ConselhoAlunoFeedbackServiceTest {
    @InjectMocks
    private ConselhoAlunoFeedbackService service;
    @Mock
    private ConselhoAlunoFeedbackMapper mapper;
    @Mock
    private ConselhoAlunoFeedbackRepository repository;
    @Mock
    private PedagogicoRepository pedagogicoRepository;
    @Mock
    private AlunoRepository alunoRepository;
    @Mock
    private ConselhoRepository conselhoRepository;

    @Test
    void create() {
        ConselhoAlunoFeedbackRequestDTO request = new ConselhoAlunoFeedbackRequestDTO(
                1L,
                1L,
                1L,
                "teste",
                "teste",
                "teste"
        );
        ConselhoAlunoFeedback alunoFeedback = new ConselhoAlunoFeedback();
        ConselhoAlunoFeedback salvo = new ConselhoAlunoFeedback();
        Pedagogico pedagogico = new Pedagogico();
        Conselho conselho = new Conselho();
        Aluno aluno = new Aluno();
        ConselhoAlunoFeedbackResponseDTO response = new ConselhoAlunoFeedbackResponseDTO(1L,
                1L,
                1L,
                "henrique",
                1L,
                "julia",
                "teste",
                "teste",
                "teste"
        );

        when(mapper.paraEntidade(request)).thenReturn(alunoFeedback);
        when(conselhoRepository.findById(request.idConselho())).thenReturn(Optional.of(conselho));
        when(alunoRepository.findById(request.idAluno())).thenReturn(Optional.of(aluno));
        when(pedagogicoRepository.findById(request.idPedagogico())).thenReturn(Optional.of(pedagogico));
        when(repository.existsByConselhoId(1L)).thenReturn(false);
        when(repository.save(alunoFeedback)).thenReturn(salvo);
        when(mapper.paraResposta(salvo)).thenReturn(response);

        ConselhoAlunoFeedbackResponseDTO result = service.create(request);

        assertEquals(response, result);

        verify(repository, times(1)).save(alunoFeedback);
    }

    @Test
    void create_ConselhoNaoExiste_DeveLancarExcecao () {
        ConselhoAlunoFeedbackRequestDTO request = new ConselhoAlunoFeedbackRequestDTO(
                99L,
                99L,
                99L,
                "teste",
                "teste",
                "teste"
        );
        ConselhoAlunoFeedback alunoFeedback = new ConselhoAlunoFeedback();

        when(mapper.paraEntidade(request)).thenReturn(alunoFeedback);
        when(conselhoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ConselhoNaoExiste.class, () -> {
            service.create(request);
        });

        verify(repository, never()).save(any());
    }

    @Test
    void create_AlunoNaoExiste_DeveLancarExcecao () {
        ConselhoAlunoFeedbackRequestDTO request = new ConselhoAlunoFeedbackRequestDTO(
                99L,
                99L,
                99L,
                "teste",
                "teste",
                "teste"
        );
        ConselhoAlunoFeedback alunoFeedback = new ConselhoAlunoFeedback();
        Conselho conselho = new Conselho();

        when(mapper.paraEntidade(request)).thenReturn(alunoFeedback);
        // preciso fazer de conta que existe conselho para conseguir fazer teste
        when(conselhoRepository.findById(request.idConselho())).thenReturn(Optional.of(conselho));
        when(alunoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(AlunoNaoExisteException.class, () -> {
            service.create(request);
        });

        verify(repository, never()).save(any());
    }

    @Test
    void create_PedagogicoNaoExiste_DeveLancarExcecao () {
        ConselhoAlunoFeedbackRequestDTO request = new ConselhoAlunoFeedbackRequestDTO(
                99L,
                99L,
                99L,
                "teste",
                "teste",
                "teste"
        );
        ConselhoAlunoFeedback alunoFeedback = new ConselhoAlunoFeedback();
        Conselho conselho = new Conselho();
        Aluno aluno = new Aluno();

        when(mapper.paraEntidade(request)).thenReturn(alunoFeedback);
        when(conselhoRepository.findById(request.idConselho())).thenReturn(Optional.of(conselho));
        when(alunoRepository.findById(request.idAluno())).thenReturn(Optional.of(aluno));
        when(pedagogicoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(PedagogicoNaoExiste.class, () -> {
           service.create(request);
        });

        verify(repository, never()).save(any());
    }

    @Test
    void create_AlunofeedbackExiste_deveLancarExcecao () {
        ConselhoAlunoFeedbackRequestDTO request = new ConselhoAlunoFeedbackRequestDTO(
                1L,
                1L,
                1L,
                "teste",
                "teste",
                "teste"
        );
        ConselhoAlunoFeedback alunoFeedback = new ConselhoAlunoFeedback();
        Conselho conselho = new Conselho();
        Aluno aluno = new Aluno();
        Pedagogico pedagogico = new Pedagogico();

        when(mapper.paraEntidade(request)).thenReturn(alunoFeedback);
        when(conselhoRepository.findById(1L)).thenReturn(Optional.of(conselho));
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(pedagogicoRepository.findById(1L)).thenReturn(Optional.of(pedagogico));
        when(repository.existsByConselhoId(request.idConselho())).thenReturn(true);

        assertThrows(ConselhoAlunoFeedbackExisteException.class, () -> {
            service.create(request);
        });

        verify(repository, never()).save(any());
    }

    @Test
    void buscarTodos() {
        ConselhoAlunoFeedbackResponseDTO response = new ConselhoAlunoFeedbackResponseDTO(
                1L,
                1L,
                1L,
                "henrique",
                1L,
                "julia",
                "teste",
                "teste",
                "teste"
                );
        ConselhoAlunoFeedback alunoFeedback = new ConselhoAlunoFeedback();

        when(repository.findAll()).thenReturn(List.of(alunoFeedback));
        when(mapper.paraResposta(alunoFeedback)).thenReturn(response);

        List<ConselhoAlunoFeedbackResponseDTO> result = service.buscarTodos();

        assertEquals(1, result.size());
        assertEquals(response, result.get(0));

        verify(repository, times(1)).findAll();
    }

    @Test
    void buscarPorId() {
        ConselhoAlunoFeedback alunoFeedback = new ConselhoAlunoFeedback();
        ConselhoAlunoFeedbackResponseDTO response = new ConselhoAlunoFeedbackResponseDTO(
                1L,
                1L,
                1L,
                "henrique",
                1L,
                "julia",
                "teste",
                "teste",
                "teste"
        );

        when(repository.findById(1L)).thenReturn(Optional.of(alunoFeedback));
        when(mapper.paraResposta(alunoFeedback)).thenReturn(response);

        ConselhoAlunoFeedbackResponseDTO result = service.buscarPorId(1L);

        assertEquals(response, result);
    }

    @Test
    void update() {
        ConselhoAlunoFeedbackRequestDTO request = new ConselhoAlunoFeedbackRequestDTO(
                1L,
                1L,
                1L,
                "teste",
                "teste",
                "teste"
        );
        ConselhoAlunoFeedback existente = new ConselhoAlunoFeedback();
        ConselhoAlunoFeedback atualizado = new ConselhoAlunoFeedback();
        ConselhoAlunoFeedbackResponseDTO response = new ConselhoAlunoFeedbackResponseDTO(
                1L,
                1L,
                1L,
                "henrique",
                1L,
                "julia",
                "teste",
                "teste",
                "teste"
        );

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(mapper.verificarUpdate(request, existente)).thenReturn(atualizado);
        when(repository.save(atualizado)).thenReturn(atualizado);
        when(mapper.paraResposta(atualizado)).thenReturn(response);

        ConselhoAlunoFeedbackResponseDTO result = service.update(1L, request);

        assertEquals(response, result);

        verify(repository).findById(1L);
        verify(mapper).verificarUpdate(request, existente);
        verify(repository, times(1)).save(atualizado);
        verify(mapper).paraResposta(atualizado);
    }

    @Test
    void alunoFeedbackNaoExiste_DeveLancarExcecao () {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ConselhoAlunoFeedbackNaoExisteException.class, () -> {
            service.buscarPorId(1L);
        });

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void delete() {
        when(repository.existsById(1L)).thenReturn(true);
        service.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}