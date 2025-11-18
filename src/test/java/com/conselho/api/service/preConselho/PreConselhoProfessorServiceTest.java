package com.conselho.api.service.preConselho;

import com.conselho.api.dto.mapper.preConselho.PreConselhoProfessorMapper;
import com.conselho.api.dto.request.preConselho.PreConselhoProfessorRequestDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoProfessorResponseDTO;
import com.conselho.api.exception.preConselhoProfessor.PreConselhoProfessorNaoExisteException;
import com.conselho.api.model.UnidadeCurricular;
import com.conselho.api.model.entity.Professor;
import com.conselho.api.model.preConselho.PreConselho;
import com.conselho.api.model.preConselho.PreConselhoProfessor;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.PreConselhoProfessorRepository;
import com.conselho.api.repository.UnidadeCurricularRepository;
import com.conselho.api.repository.entity.ProfessorRepository;
import com.conselho.api.repository.preConselho.PreConselhoRepository;
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
class PreConselhoProfessorServiceTest {

    @InjectMocks
    private PreConselhoProfessorService service;

    @Mock
    private PreConselhoRepository preConselhoRepository;

    @Mock
    private PreConselhoProfessorRepository repository;

    @Mock
    private ProfessorRepository professorRepository;

    @Mock
    private UnidadeCurricularRepository unidadeCurricularRepository;

    @Mock
    private ConselhoRepository conselhoRepository;

    @Mock
    private PreConselhoProfessorMapper mapper;


    @Test
    void criarPreConselhoProfessor_ComSucesso() {
        PreConselhoProfessorRequestDTO requestDTO = new PreConselhoProfessorRequestDTO(1L, 1L, 1L, "TESTE", "TESTE", "TESTE");
        PreConselho preConselho = new PreConselho();
        PreConselhoProfessor preConselhoProfessor = new PreConselhoProfessor();
        PreConselhoProfessor salvo = new PreConselhoProfessor();
        PreConselhoProfessorResponseDTO responseDTO = new PreConselhoProfessorResponseDTO(1L, 1L, 1L, "TESTE", 1L, "TESTE", "TESTE", "TESTE", "TESTE");

        when(mapper.paraEntidade(requestDTO)).thenReturn(preConselhoProfessor);
        when(preConselhoRepository.findById(1L)).thenReturn(Optional.of(preConselho));
        UnidadeCurricular unidadeCurricular = new UnidadeCurricular();
        when(unidadeCurricularRepository.findById(1L)).thenReturn(Optional.of(unidadeCurricular));
        Professor professor = new Professor();
        when(professorRepository.findById(1L)).thenReturn(Optional.of(professor));  // Mock professorRepository
        when(repository.save(preConselhoProfessor)).thenReturn(salvo);
        when(mapper.paraResposta(salvo)).thenReturn(responseDTO);

        PreConselhoProfessorResponseDTO result = service.criarPreConselhoProfessor(requestDTO);

        assertEquals(responseDTO, result);
    }

    @Test
    void criarPreConselhoProfessor_DeveLancarExcecao(){
        PreConselhoProfessorRequestDTO requestDTO = new PreConselhoProfessorRequestDTO(1L, 1L, 1L, "TESTE", "TESTE", "TESTE");
        PreConselhoProfessor preConselhoProfessor = new PreConselhoProfessor();

        lenient().when(mapper.paraEntidade(requestDTO)).thenReturn(preConselhoProfessor);
        lenient().when(conselhoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PreConselhoProfessorNaoExisteException.class, ()->{
            service.criarPreConselhoProfessor(requestDTO);
        });

        verify(professorRepository, never()).save(any());
    }

    @Test
    void listarPreConselhoProfessor() {
        PreConselhoProfessorResponseDTO preConselhoProfessorResponseDTO = new PreConselhoProfessorResponseDTO(1L, 1L, 1L, "TESTE", 1L, "TESTE", "TESTE", "TESTE", "TESTE");
        PreConselhoProfessor preConselhoProfessor = new PreConselhoProfessor();

        when(repository.findAll()).thenReturn(List.of(preConselhoProfessor));
        when(mapper.paraResposta(preConselhoProfessor)).thenReturn(preConselhoProfessorResponseDTO);

        List<PreConselhoProfessorResponseDTO> result = service.listarPreConselhoProfessor();

        assertEquals(1, result.size());
        assertEquals(preConselhoProfessorResponseDTO, result.get(0));

        verify(repository, times(1)).findAll();
    }

    @Test
    void buscarPreConselhoProfessorPorId() {
        PreConselhoProfessor preConselhoProfessor = new PreConselhoProfessor();
        PreConselhoProfessorResponseDTO preConselhoProfessorResponseDTO = new PreConselhoProfessorResponseDTO(1L, 1L, 1L, "TESTE", 1L, "TESTE", "TESTE", "TESTE", "TESTE");

        lenient().when(repository.findById(1L)).thenReturn(Optional.of(preConselhoProfessor));
        lenient().when(mapper.paraResposta(preConselhoProfessor)).thenReturn(preConselhoProfessorResponseDTO);

        PreConselhoProfessorResponseDTO result = new PreConselhoProfessorResponseDTO(1L, 1L, 1L, "TESTE", 1L, "TESTE", "TESTE", "TESTE", "TESTE");

        assertEquals(preConselhoProfessorResponseDTO, result);
    }

    @Test
    void atualizarPreConselhoProfessor() {
        PreConselhoProfessorRequestDTO requestDTO = new PreConselhoProfessorRequestDTO(1L, 1L, 1L, "TESTE", "TESTE", "TESTE");
        PreConselhoProfessor existe = new PreConselhoProfessor();
        PreConselhoProfessor atualizado = new PreConselhoProfessor();
        PreConselhoProfessorResponseDTO responseDTO = new PreConselhoProfessorResponseDTO(1L, 1L, 1L, "TESTE", 1L, "TESTE", "TESTE", "TESTE", "TESTE");

        when(repository.findById(1L)).thenReturn(Optional.of(existe));
        when(mapper.paraUpdate(requestDTO, existe)).thenReturn(atualizado);
        when(repository.save(atualizado)).thenReturn(atualizado);
        when(mapper.paraResposta(atualizado)).thenReturn(responseDTO);

        PreConselhoProfessorResponseDTO result = service.atualizarPreConselhoProfessor(1L, requestDTO);

        assertEquals(responseDTO, result);

        verify(repository).findById(1L);
        verify(mapper).paraUpdate(requestDTO, existe);
        verify(repository).save(atualizado);
        verify(mapper).paraResposta(atualizado);
    }

    @Test
    void PreConselhoProfessorNaoExiste_DeveLancarExcecao(){
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PreConselhoProfessorNaoExisteException.class, () ->{
            service.buscarPreConselhoProfessorPorId(1L);

            verify(repository, times(1)).findById(1L);
        });
    }

    @Test
    void deletarPreConselhoProfessor() {
        when(repository.existsById(1L)).thenReturn(true);
        service.deletarPreConselhoProfessor(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void DeveLancarErroQuando_deletarPreConselhoProfessor_NaoExiste() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(PreConselhoProfessorNaoExisteException.class, ()->{
            service.deletarPreConselhoProfessor(1L);
        });
        verify(repository, never()).deleteById(any());

    }
    }