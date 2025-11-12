package com.conselho.api.service.preConselho;

import com.conselho.api.dto.mapper.preConselho.PreConselhoMapper;
import com.conselho.api.dto.request.preConselho.PreConselhoRequestDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoResponseDTO;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.preConselho.PreConselhoExisteException;
import com.conselho.api.exception.preConselho.PreConselhoNaoExisteException;
import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.model.preConselho.PreConselho;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.preConselho.PreConselhoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class PreConselhoServiceTest {
    @InjectMocks
    private PreConselhoService service;
    @Mock
    private PreConselhoRepository preConselhoRepository;
    @Mock
    private ConselhoRepository conselhoRepository;
    @Mock
    private PreConselhoMapper mapper;

    @Test
    void criarPreConselhoAutomatico_ComSucesso() {

        PreConselhoRequestDTO request = new PreConselhoRequestDTO(1L);
        Conselho conselho = new Conselho();
        PreConselho preConselho = new PreConselho();
        PreConselho salvo = new PreConselho();
        PreConselhoResponseDTO response = new PreConselhoResponseDTO(1L, 1L);

        when(mapper.paraEntidade(request)).thenReturn(preConselho);
        when(conselhoRepository.findById(1L)).thenReturn(Optional.of(conselho));
        when(preConselhoRepository.existsByConselhoId(1L)).thenReturn(false);
        when(preConselhoRepository.save(preConselho)).thenReturn(salvo);
        when(mapper.paraResposta(salvo)).thenReturn(response);

        PreConselhoResponseDTO result = service.criarPreConselhoAutomatico(request);

        assertEquals(response, result);

        verify(preConselhoRepository, times(1)).save(preConselho);
    }

    @Test
    void criarPreConselhoAutomatico_ConselhoNaoExiste_DeveLancarExcecao () {
        PreConselhoRequestDTO request = new PreConselhoRequestDTO(99L);
        PreConselho preConselho = new PreConselho();
        Conselho conselho = new Conselho();

        when(mapper.paraEntidade(request)).thenReturn(preConselho);
        when(conselhoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ConselhoNaoExiste.class, () -> {
            service.criarPreConselhoAutomatico(request);
        });

        verify(preConselhoRepository, never()).save(any());

    }

    @Test
    void criarPreConselhoAutomatico_PreConselhoJaExiste_DeveLancarExcecao () {
        PreConselhoRequestDTO request = new PreConselhoRequestDTO(1L);
        PreConselho preConselho = new PreConselho();
        Conselho conselho = new Conselho();

        when(mapper.paraEntidade(request)).thenReturn(preConselho);
        when(conselhoRepository.findById(1L)).thenReturn(Optional.of(conselho));
        when(preConselhoRepository.existsByConselhoId(1L)).thenReturn(true);

        assertThrows(PreConselhoExisteException.class, () -> {
            service.criarPreConselhoAutomatico(request);
        });

        verify(preConselhoRepository, never()).save(any());
    }

    @Test
    void buscarTodos() {
        PreConselhoResponseDTO preConselhoResponseDTO = new PreConselhoResponseDTO(1L, 1L);
        PreConselho preConselho = new PreConselho();

        when(preConselhoRepository.findAll()).thenReturn(List.of(preConselho));
        when(mapper.paraResposta(preConselho)).thenReturn(preConselhoResponseDTO);

        List<PreConselhoResponseDTO> result = service.buscarTodos();

        assertEquals(1, result.size());
        assertEquals(preConselhoResponseDTO, result.get(0));

        verify(preConselhoRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId() {
        PreConselho preConselho = new PreConselho();
        PreConselhoResponseDTO preConselhoResponseDTO = new PreConselhoResponseDTO(1L, 1L);

        when(preConselhoRepository.findById(1L)).thenReturn(Optional.of(preConselho));
        when(mapper.paraResposta(preConselho)).thenReturn(preConselhoResponseDTO);

        PreConselhoResponseDTO result = service.buscarPorId(1L);

        assertEquals(preConselhoResponseDTO, result);
    }

    @Test
    void update() {
        PreConselhoRequestDTO request = new PreConselhoRequestDTO(1L);
        PreConselho existente = new PreConselho();
        PreConselho atualizado = new PreConselho();
        PreConselhoResponseDTO response = new PreConselhoResponseDTO(1L, 1L);

        when(preConselhoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(mapper.verificarUpdate(request, existente)).thenReturn(atualizado);
        when(preConselhoRepository.save(atualizado)).thenReturn(atualizado);
        when(mapper.paraResposta(atualizado)).thenReturn(response);

        PreConselhoResponseDTO result = service.update(1L, request);

        assertEquals(response, result);

        verify(preConselhoRepository).findById(1L);
        verify(mapper).verificarUpdate(request, existente);
        verify(preConselhoRepository).save(atualizado);
        verify(mapper).paraResposta(atualizado);
    }

    // Tanto para update e buscar por id
    @Test
    void PreConselhoNaoExiste_DeveLancarExcecao () {
        when(preConselhoRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(PreConselhoNaoExisteException.class, () -> service.buscarPorId(1L));

        verify(preConselhoRepository, times(1)).findById(1L);
    }

    @Test
    void deletarPreConselho_ComSucesso() {
        when(preConselhoRepository.existsById(1L)).thenReturn(true);
        service.delete(1L);

        verify(preConselhoRepository, times(1)).deleteById(1L);
    }

    @Test
    void deveLancarErro_QuandoPreConselhoNaoExiste_aoDeletar() {
        when(preConselhoRepository.existsById(1L)).thenReturn(false);

        assertThrows(PreConselhoNaoExisteException.class, () -> service.delete(1L));

        verify(preConselhoRepository, never()).deleteById(any());
    }
}