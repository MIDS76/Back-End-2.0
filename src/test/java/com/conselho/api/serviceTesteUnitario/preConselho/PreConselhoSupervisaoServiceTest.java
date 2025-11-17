package com.conselho.api.serviceTesteUnitario.preConselho;

import com.conselho.api.dto.mapper.preConselho.PreConselhoSupervisaoMapper;
import com.conselho.api.dto.request.preConselho.PreConselhoSupervisaoRequestDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoSupervisaoResponseDTO;
import com.conselho.api.exception.preConselho.PreConselhoNaoExisteException;
import com.conselho.api.exception.preConselhoSupervisao.PreConselhoSupervisaoNaoExisteException;
import com.conselho.api.model.preConselho.PreConselho;
import com.conselho.api.model.preConselho.PreConselhoSupervisao;
import com.conselho.api.repository.preConselho.PreConselhoRepository;
import com.conselho.api.repository.preConselho.PreConselhoSupervisaoRepository;
import com.conselho.api.service.preConselho.PreConselhoSupervisaoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class PreConselhoSupervisaoServiceTest {
    @InjectMocks
    private PreConselhoSupervisaoService service;
    @Mock
    private PreConselhoSupervisaoRepository repository;
    @Mock
    private PreConselhoRepository preConselhoRepository;
    @Mock
    private PreConselhoSupervisaoMapper mapper;

    @Test
    void criarPreConselhoSupervisao() {
        PreConselhoSupervisaoRequestDTO request = new PreConselhoSupervisaoRequestDTO(1L, "teste", "teste", "teste");
        PreConselhoSupervisao preConselhoSupervisao = new PreConselhoSupervisao();
        PreConselhoSupervisao salvo = new PreConselhoSupervisao();
        PreConselho preConselho = new PreConselho();
        PreConselhoSupervisaoResponseDTO response = new PreConselhoSupervisaoResponseDTO(1L, 1L, "teste", "teste", "teste");

        when(mapper.paraEntidade(request)).thenReturn(preConselhoSupervisao);
        when(preConselhoRepository.findById(request.idPreConselho())).thenReturn(Optional.of(preConselho));
        when(repository.save(preConselhoSupervisao)).thenReturn(salvo);
        when(mapper.paraResposta(salvo)).thenReturn(response);

        PreConselhoSupervisaoResponseDTO result = service.criarPreConselhoSupervisao(request);

        assertEquals(response, result);

        verify(repository, times(1)).save(preConselhoSupervisao);
    }

    @Test
    void criarPreConselhoSupervisao_PreConselhoNaoExiste_DeveLancarExcecao () {
        PreConselhoSupervisaoRequestDTO request = new PreConselhoSupervisaoRequestDTO(99L, "teste", "teste", "teste");
        PreConselhoSupervisao preConselhoSupervisao = new PreConselhoSupervisao();

        when(mapper.paraEntidade(request)).thenReturn(preConselhoSupervisao);
        when (preConselhoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(PreConselhoNaoExisteException.class, () -> {
            service.criarPreConselhoSupervisao(request);
        });

        verify(repository, never()).save(any());
    }

    @Test
    void listarTodos() {
        PreConselhoSupervisao preConselhoSupervisao = new PreConselhoSupervisao();
        PreConselhoSupervisaoResponseDTO response = new PreConselhoSupervisaoResponseDTO(1L, 1L, "teste", "teste", "teste");

        when(repository.findAll()).thenReturn(List.of(preConselhoSupervisao));
        when(mapper.paraResposta(preConselhoSupervisao)).thenReturn(response);

        List<PreConselhoSupervisaoResponseDTO> result = service.listarTodos();

        assertEquals(1, result.size());
        assertEquals(response, result.get(0));

        verify(repository, times(1)).findAll();
    }

    @Test
    void buscarPorId() {
        PreConselhoSupervisao preConselhoSupervisao = new PreConselhoSupervisao();
        PreConselhoSupervisaoResponseDTO response = new PreConselhoSupervisaoResponseDTO(1L, 1L, "teste", "teste", "teste");

        when(repository.findById(1L)).thenReturn(Optional.of(preConselhoSupervisao));
        when(mapper.paraResposta(preConselhoSupervisao)).thenReturn(response);

        PreConselhoSupervisaoResponseDTO result = service.buscarPorId(1L);

        assertEquals(response, result);

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void buscarPorId_preConselhoSupervisaoNaoExiste_DeveLancarExcecao () {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PreConselhoSupervisaoNaoExisteException.class, () ->{
            service.buscarPorId(1L);
        });

        verify(repository).findById(1L);
    }

    @Test
    void atualizarPreConselhoSupervisao() {
        PreConselhoSupervisaoRequestDTO request = new PreConselhoSupervisaoRequestDTO(1L, "teste", "teste", "teste");
        PreConselhoSupervisao existente = new PreConselhoSupervisao();
        PreConselhoSupervisao atualizado = new PreConselhoSupervisao();
        PreConselhoSupervisaoResponseDTO response = new PreConselhoSupervisaoResponseDTO(1L, 1L, "teste", "teste", "teste");

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(mapper.paraUpdate(request, existente)).thenReturn(atualizado);
        when(repository.save(atualizado)).thenReturn(atualizado);
        when(mapper.paraResposta(atualizado)).thenReturn(response);

        PreConselhoSupervisaoResponseDTO result = service.atualizarPreConselhoSupervisao(1L, request);

        assertEquals(response, result);

        verify(repository).findById(1L);
        verify(mapper).paraUpdate(request, existente);
        verify(repository, times(1)).save(atualizado);
        verify(mapper).paraResposta(atualizado);
    }

    @Test
    void atualizarPreConselhoSupervisao_preConselhoSupervisaoNaoExiste_DeveLancarExcecao () {
        PreConselhoSupervisaoRequestDTO request = new PreConselhoSupervisaoRequestDTO(1L, "teste", "teste", "teste");

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PreConselhoSupervisaoNaoExisteException.class, () ->{
            service.atualizarPreConselhoSupervisao(1L, request);
        });

        verify(repository, never()).save(any());
    }

    @Test
    void deletarPreConselhoSupervisao() {
        when(repository.existsById(1L)).thenReturn(true);

        service.deletarPreConselhoSupervisao(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void delete_PreConselhoSupervisaoNaoExiste_DeveLancarExcecao () {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(PreConselhoSupervisaoNaoExisteException.class, () ->{
            service.deletarPreConselhoSupervisao(1L);
        });

        verify(repository, never()).deleteById(1L);
    }
}