package com.conselho.api.serviceTesteUnitario.preConselho;

import com.conselho.api.dto.mapper.preConselho.PreConselhoPedagogicoMapper;
import com.conselho.api.dto.request.preConselho.PreConselhoPedagogicoRequestDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoPedagogicoResponseDTO;
import com.conselho.api.exception.preConselho.PreConselhoNaoExisteException;
import com.conselho.api.exception.preConselhoPedagogico.PreConselhoPedagogicoNaoExisteException;
import com.conselho.api.exception.preConselhoSupervisao.PreConselhoSupervisaoNaoExisteException;
import com.conselho.api.model.preConselho.PreConselho;
import com.conselho.api.model.preConselho.PreConselhoPedagogico;
import com.conselho.api.repository.preConselho.PreConselhoPedagogicoRepository;
import com.conselho.api.repository.preConselho.PreConselhoRepository;
import com.conselho.api.service.preConselho.PreConselhoPedagogicoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PreConselhoPedagogicoServiceTest {

    @InjectMocks
    private PreConselhoPedagogicoService service;

    @Mock
    private PreConselhoPedagogicoRepository repository;

    @Mock
    private PreConselhoRepository preConselhoRepository;

    @Mock
    private PreConselhoPedagogicoMapper mapper;
    @Test
    void criarPreConselhoPedagogico() {
        PreConselhoPedagogicoRequestDTO request = new PreConselhoPedagogicoRequestDTO(1L, "teste", "teste", "teste");
        PreConselhoPedagogico preConselhoPedagogico = new PreConselhoPedagogico();
        PreConselhoPedagogico salvo = new PreConselhoPedagogico();
        PreConselho preConselho = new PreConselho();
        PreConselhoPedagogicoResponseDTO response = new PreConselhoPedagogicoResponseDTO(1L, 1L, "teste", "teste", "teste");

        when(mapper.paraEntidade(request)).thenReturn(preConselhoPedagogico);
        when(preConselhoRepository.findById(request.idPreConselho())).thenReturn(Optional.of(preConselho));
        when(repository.save(preConselhoPedagogico)).thenReturn(salvo);
        when(mapper.paraResposta(salvo)).thenReturn(response);

        PreConselhoPedagogicoResponseDTO result = service.criarPreConselhoPedagogico(request);

        assertEquals(response, result);

        verify(repository, times(1)).save(preConselhoPedagogico);

    }

    @Test
    void criarPreConselhoPedagogico_PreConselhoNaoExiste_DeveLancarExcecao () {
        PreConselhoPedagogicoRequestDTO request = new PreConselhoPedagogicoRequestDTO(99L, "teste", "teste", "teste");
        PreConselhoPedagogico preConselhoPedagogico = new PreConselhoPedagogico();

        when(mapper.paraEntidade(request)).thenReturn(preConselhoPedagogico);
        when (preConselhoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(PreConselhoNaoExisteException.class, () -> {
            service.criarPreConselhoPedagogico(request);
        });

        verify(repository, never()).save(any());
    }

    @Test
    void listarTodos() {
        PreConselhoPedagogico preConselhoPedagogico = new PreConselhoPedagogico();
        PreConselhoPedagogicoResponseDTO response = new PreConselhoPedagogicoResponseDTO(1L, 1L, "teste", "teste", "teste");

        when(repository.findAll()).thenReturn(List.of(preConselhoPedagogico));
        when(mapper.paraResposta(preConselhoPedagogico)).thenReturn(response);

        List<PreConselhoPedagogicoResponseDTO> result = service.listarTodos();

        assertEquals(1, result.size());
        assertEquals(response, result.get(0));

        verify(repository).findAll();
    }

    @Test
    void buscarPorId() {
        PreConselhoPedagogico preConselhoPedagogico = new PreConselhoPedagogico();
        PreConselhoPedagogicoResponseDTO response = new PreConselhoPedagogicoResponseDTO(1L, 1L, "teste", "teste", "teste");

        when(repository.findById(1L)).thenReturn(Optional.of(preConselhoPedagogico));
        when(mapper.paraResposta(preConselhoPedagogico)).thenReturn(response);

        PreConselhoPedagogicoResponseDTO result = service.buscarPorId(1L);

        assertEquals(response, result);

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void buscarPorId_preConselhoPedagogicoNaoExiste_DeveLancarExcecao () {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PreConselhoPedagogicoNaoExisteException.class, () ->{
            service.buscarPorId(1L);
        });

        verify(repository).findById(1L);
    }

    @Test
    void atualizarPreConselhoPedagogico() {
        PreConselhoPedagogicoRequestDTO request = new PreConselhoPedagogicoRequestDTO(1L, "teste", "teste", "teste");
        PreConselhoPedagogico existente = new PreConselhoPedagogico();
        PreConselhoPedagogico atualizado = new PreConselhoPedagogico();
        PreConselhoPedagogicoResponseDTO response = new PreConselhoPedagogicoResponseDTO(1L, 1L, "teste", "teste", "teste");

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(mapper.paraUpdate(request, existente)).thenReturn(atualizado);
        when(repository.save(atualizado)).thenReturn(atualizado);
        when(mapper.paraResposta(atualizado)).thenReturn(response);

        PreConselhoPedagogicoResponseDTO result = service.atualizarPreConselhoPedagogico(1L, request);

        assertEquals(response, result);

        verify(repository).findById(1L);
        verify(mapper).paraUpdate(request, existente);
        verify(repository, times(1)).save(atualizado);
        verify(mapper).paraResposta(atualizado);
    }

    @Test
    void atualizarPreConselhoPedagogico_preConselhoPedagogicoNaoExiste_DeveLancarExcecao () {
        PreConselhoPedagogicoRequestDTO request = new PreConselhoPedagogicoRequestDTO(1L, "teste", "teste", "teste");

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PreConselhoPedagogicoNaoExisteException.class, () ->{
            service.atualizarPreConselhoPedagogico(1L, request);
        });

        verify(repository, never()).save(any());
    }

    @Test
    void deletarPreConselhoPedagogico() {
        when(repository.existsById(1L)).thenReturn(true);

        service.deletarPreConselhoPedagogico(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void delete_PreConselhoPedagogicoNaoExiste_DeveLancarExcecao () {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(PreConselhoPedagogicoNaoExisteException.class, () ->{
            service.deletarPreConselhoPedagogico(1L);
        });

        verify(repository, never()).deleteById(1L);
    }
}