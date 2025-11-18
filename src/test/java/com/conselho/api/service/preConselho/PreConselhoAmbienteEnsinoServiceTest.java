package com.conselho.api.service.preConselho;

import com.conselho.api.dto.mapper.preConselho.PreConselhoAmbienteEnsinoMapper;
import com.conselho.api.dto.request.preConselho.PreConselhoAmbienteEnsinoRequestDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoAmbienteEnsinoResponseDTO;
import com.conselho.api.exception.preConselhoAmbienteEnsino.PreConselhoAmbienteEnsinoNaoExisteException;
import com.conselho.api.model.preConselho.PreConselho;
import com.conselho.api.model.preConselho.PreConselhoAmbienteEnsino;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.preConselho.PreConselhoAmbienteEnsinoRepository;
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
class PreConselhoAmbienteEnsinoServiceTest {

    @InjectMocks
    private PreConselhoAmbienteEnsinoService service;

    @Mock
    private PreConselhoRepository repository;
    @Mock
    private PreConselhoAmbienteEnsinoRepository preConselhoAmbienteEnsinoRepository;
    @Mock
    private ConselhoRepository conselhoRepository;
    @Mock
    private PreConselhoAmbienteEnsinoMapper mapper;


    @Test
    void criarPreConselhoAmbienteEnsino_ComSucesso() {
        PreConselhoAmbienteEnsinoRequestDTO request = new PreConselhoAmbienteEnsinoRequestDTO(1L, "TESTE", "TESTE", "TESTE");
        PreConselhoAmbienteEnsino preConselhoAmbienteEnsino = new PreConselhoAmbienteEnsino();
        PreConselhoAmbienteEnsino salvo = new PreConselhoAmbienteEnsino();
        PreConselhoAmbienteEnsinoResponseDTO response = new PreConselhoAmbienteEnsinoResponseDTO(1L,1L, "TESTE", "TESTE", "TESTE");
        PreConselho preConselho = new PreConselho();

        when(mapper.paraEntidade(request)).thenReturn(preConselhoAmbienteEnsino);
        when(repository.findById(1L)).thenReturn(Optional.of(preConselho));
        when(preConselhoAmbienteEnsinoRepository.save(preConselhoAmbienteEnsino)).thenReturn(salvo);
        when(mapper.paraResposta(salvo)).thenReturn(response);

        PreConselhoAmbienteEnsinoResponseDTO result = service.criarPreConselhoAmbienteEnsino(request);

        assertEquals(response, result);
}
    @Test
    void criarPreConselhoAmbienteEnsino_DeveLancarExcecao() {
        PreConselhoAmbienteEnsinoRequestDTO requestDTO = new PreConselhoAmbienteEnsinoRequestDTO(1L, "TESTE", "TESTE", "TESTE");
        PreConselhoAmbienteEnsino preConselhoAmbienteEnsino = new PreConselhoAmbienteEnsino();

        lenient().when(mapper.paraEntidade(requestDTO)).thenReturn(preConselhoAmbienteEnsino);
        lenient().when(conselhoRepository.findById(99L)).thenReturn(Optional.empty());


        assertThrows(PreConselhoAmbienteEnsinoNaoExisteException.class, () -> {
            service.criarPreConselhoAmbienteEnsino(requestDTO);
        });

        verify(preConselhoAmbienteEnsinoRepository, never()).save(any());

    }

        @Test
    void listarTodos_preConselhoAmbiente() {
        PreConselhoAmbienteEnsinoResponseDTO preConselhoAmbienteEnsinoResponseDTO = new PreConselhoAmbienteEnsinoResponseDTO(1L, 1L, "TESTE", "TESTE", "TESTE");
        PreConselhoAmbienteEnsino preConselhoAmbienteEnsino = new PreConselhoAmbienteEnsino();

        when(preConselhoAmbienteEnsinoRepository.findAll()).thenReturn(List.of(preConselhoAmbienteEnsino));
        when(mapper.paraResposta(preConselhoAmbienteEnsino)).thenReturn(preConselhoAmbienteEnsinoResponseDTO);

        List<PreConselhoAmbienteEnsinoResponseDTO> result = service.listarTodos();

        assertEquals(1, result.size());
        assertEquals(preConselhoAmbienteEnsinoResponseDTO, result.get(0));

        verify(preConselhoAmbienteEnsinoRepository, times(1)).findAll();

    }

    @Test
    void buscarPorId() {
        PreConselhoAmbienteEnsino preConselhoAmbienteEnsino = new PreConselhoAmbienteEnsino();
        PreConselhoAmbienteEnsinoResponseDTO preConselhoAmbienteEnsinoResponseDTO = new PreConselhoAmbienteEnsinoResponseDTO(1L, 1L, "TESTE", "TETSE", "TESTE");

        when(preConselhoAmbienteEnsinoRepository.findById(1L)).thenReturn(Optional.of(preConselhoAmbienteEnsino));
        when(mapper.paraResposta(preConselhoAmbienteEnsino)).thenReturn(preConselhoAmbienteEnsinoResponseDTO);

        PreConselhoAmbienteEnsinoResponseDTO result = service.buscarPorId(1L);

        assertEquals(preConselhoAmbienteEnsinoResponseDTO, result);
    }

    @Test
    void atualizarPreConselhoAmbienteEnsino_ComSucesso() {
        PreConselhoAmbienteEnsinoRequestDTO request = new PreConselhoAmbienteEnsinoRequestDTO(1L, "TESTE", "TESTE", "TESTE");
        PreConselhoAmbienteEnsino preConselhoAmbienteEnsinoExistente = new PreConselhoAmbienteEnsino();
        PreConselhoAmbienteEnsino preConselhoAmbienteEnsinoAtualizado = new PreConselhoAmbienteEnsino();
        PreConselhoAmbienteEnsinoResponseDTO response = new PreConselhoAmbienteEnsinoResponseDTO(1L, 1L, "TESTE", "TESTE", "TESTE");

        when(preConselhoAmbienteEnsinoRepository.findById(1L)).thenReturn(Optional.of(preConselhoAmbienteEnsinoExistente));
        when(mapper.paraUpdate(request, preConselhoAmbienteEnsinoExistente)).thenReturn(preConselhoAmbienteEnsinoAtualizado);
        when(preConselhoAmbienteEnsinoRepository.save(preConselhoAmbienteEnsinoAtualizado)).thenReturn(preConselhoAmbienteEnsinoAtualizado);
        when(mapper.paraResposta(preConselhoAmbienteEnsinoAtualizado)).thenReturn(response);

        PreConselhoAmbienteEnsinoResponseDTO result = service.atualizarPreConselhoAmbienteEnsino(1L, request);

        assertEquals(response, result);

        verify(preConselhoAmbienteEnsinoRepository).findById(1L);
        verify(mapper).paraUpdate(request, preConselhoAmbienteEnsinoExistente);
        verify(preConselhoAmbienteEnsinoRepository).save(preConselhoAmbienteEnsinoAtualizado);
        verify(mapper).paraResposta(preConselhoAmbienteEnsinoAtualizado);
    }

    @Test
    void PreConselhoEnsinoNaoExiste_DeveLancarExcecao () {
        when(preConselhoAmbienteEnsinoRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(PreConselhoAmbienteEnsinoNaoExisteException.class, () -> service.buscarPorId(1L));

        verify(preConselhoAmbienteEnsinoRepository, times(1)).findById(1L);
    }


    @Test
    void deletarPreConselhoAmbienteEnsino() {
        when(preConselhoAmbienteEnsinoRepository.existsById(1L)).thenReturn(true);
        service.deletarPreConselhoAmbienteEnsino(1L);

        verify(preConselhoAmbienteEnsinoRepository, times(1)).deleteById(1L);
    }

    @Test
    void deveLancarErro_QuandoPreConselhoAmbienteNaoExiste_aoDeletar() {
        when(preConselhoAmbienteEnsinoRepository.existsById(1L)).thenReturn(false);

        assertThrows(PreConselhoAmbienteEnsinoNaoExisteException.class, () -> service.deletarPreConselhoAmbienteEnsino(1L));

        verify(preConselhoAmbienteEnsinoRepository, never()).deleteById(any());
    }
    }
