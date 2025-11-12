package com.conselho.api.serviceTesteUnitario;

import com.conselho.api.dto.mapper.UnidadeCurricularMapper;
import com.conselho.api.dto.request.UnidadeCurricularRequestDTO;
import com.conselho.api.dto.response.UnidadeCurricularResponseDTO;
import com.conselho.api.exception.unidadeCurricular.UnidadeCurricularExisteException;
import com.conselho.api.exception.unidadeCurricular.UnidadeCurricularNaoExisteException;
import com.conselho.api.model.UnidadeCurricular;
import com.conselho.api.repository.UnidadeCurricularRepository;
import com.conselho.api.service.UnidadeCurricularService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnidadeCurricularServiceTest {

    @Mock
    private UnidadeCurricularMapper mapper;

    @Mock
    private UnidadeCurricularRepository repository;

    @InjectMocks
    private UnidadeCurricularService service;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void deveCriarUnidadeCurricular_ComSucesso() {
        UnidadeCurricularRequestDTO requestDTO = new UnidadeCurricularRequestDTO("Matemática");
        UnidadeCurricular unidadeCurricular = new UnidadeCurricular();
        unidadeCurricular.setId(1L);

        UnidadeCurricularResponseDTO response = new UnidadeCurricularResponseDTO(1L, "Matemática");

        when(repository.existsByNome(requestDTO.nome()))
                .thenReturn(false);
        when(mapper.paraEntidade(requestDTO))
                .thenReturn(unidadeCurricular);
        when(repository.save(unidadeCurricular))
                .thenReturn(unidadeCurricular);
        when(mapper.paraResposta(unidadeCurricular))
                .thenReturn(response);

        UnidadeCurricularResponseDTO result = service.criarUnidadeCurricular(requestDTO);

        assertEquals(response, result);
        verify(repository, times(1)).save(unidadeCurricular);
    }

    @Test
    void deveLancarExcecao_QuandoUnidadeCurricularJaExistir() {
        UnidadeCurricularRequestDTO requestDTO = new UnidadeCurricularRequestDTO("Matemática");

        when(repository.existsByNome(requestDTO.nome())).thenReturn(true);

        assertThrows(UnidadeCurricularExisteException.class, () -> service.criarUnidadeCurricular(requestDTO));
    }

    @Test
    void deveListarUnidadesCurriculares_ComSucesso() {
        UnidadeCurricular unidadeCurricular1 = new UnidadeCurricular();
        unidadeCurricular1.setId(1L);
        UnidadeCurricular unidadeCurricular2 = new UnidadeCurricular();
        unidadeCurricular2.setId(2L);

        when(repository.findAll()).thenReturn(List.of(unidadeCurricular1, unidadeCurricular2));
        when(mapper.paraResposta(unidadeCurricular1)).thenReturn(new UnidadeCurricularResponseDTO(1L, "Matemática"));
        when(mapper.paraResposta(unidadeCurricular2)).thenReturn(new UnidadeCurricularResponseDTO(2L, "Física"));

        List<UnidadeCurricularResponseDTO> result = service.listarUnidadesCurriculares();

        assertEquals(2, result.size());
    }

    @Test
    void deveBuscarUnidadeCurricularPorId_ComSucesso() {
        UnidadeCurricular unidadeCurricular = new UnidadeCurricular();
        unidadeCurricular.setId(1L);

        when(repository.findById(1L)).thenReturn(java.util.Optional.of(unidadeCurricular));
        when(mapper.paraResposta(unidadeCurricular)).thenReturn(new UnidadeCurricularResponseDTO(1L, "Matemática"));

        UnidadeCurricularResponseDTO result = service.buscarUnidadesPorId(1L);

        assertEquals(1L, result.id());
        assertEquals("Matemática", result.nome());
    }

    @Test
    void deveLancarExcecao_QuandoUnidadeCurricularNaoExistir_AoBuscarPorId() {
        when(repository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(UnidadeCurricularNaoExisteException.class, () -> service.buscarUnidadesPorId(1L));
    }

    @Test
    void deveAtualizarUnidadeCurricular_ComSucesso() {
        UnidadeCurricularRequestDTO requestDTO = new UnidadeCurricularRequestDTO("Matemática Avançada");
        UnidadeCurricular unidadeCurricular = new UnidadeCurricular();
        unidadeCurricular.setId(1L);
        UnidadeCurricular updatedUnidadeCurricular = new UnidadeCurricular();
        updatedUnidadeCurricular.setId(1L);

        UnidadeCurricularResponseDTO response = new UnidadeCurricularResponseDTO(1L, "Matemática Avançada");

        when(repository.findById(1L)).thenReturn(java.util.Optional.of(unidadeCurricular));
        when(mapper.paraUpdate(requestDTO, unidadeCurricular)).thenReturn(updatedUnidadeCurricular);
        when(repository.save(updatedUnidadeCurricular)).thenReturn(updatedUnidadeCurricular);
        when(mapper.paraResposta(updatedUnidadeCurricular)).thenReturn(response);

        UnidadeCurricularResponseDTO result = service.atualizarUnidadeCurricular(1L, requestDTO);

        assertEquals(response, result);
        verify(repository, times(1)).save(updatedUnidadeCurricular);
    }

    @Test
    void deveLancarExcecao_QuandoUnidadeCurricularNaoExistir_AoAtualizar() {
        UnidadeCurricularRequestDTO requestDTO = new UnidadeCurricularRequestDTO("Matemática Avançada");

        when(repository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(UnidadeCurricularNaoExisteException.class, () -> service.atualizarUnidadeCurricular(1L, requestDTO));
        verify(repository, never()).save(any());
    }

    @Test
    void deveDeletarUnidadeCurricular_ComSucesso() {
        UnidadeCurricular unidadeCurricular = new UnidadeCurricular();
        unidadeCurricular.setId(1L);

        when(repository.findById(1L)).thenReturn(java.util.Optional.of(unidadeCurricular));

        UnidadeCurricularResponseDTO response = new UnidadeCurricularResponseDTO(1L, "Matemática");

        when(mapper.paraResposta(unidadeCurricular)).thenReturn(response);

        UnidadeCurricularResponseDTO result = service.deletarUnidadeCurricular(1L);

        assertEquals(response, result);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deveLancarExcecao_QuandoUnidadeCurricularNaoExistir_AoDeletar() {
        when(repository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(UnidadeCurricularNaoExisteException.class, () -> service.deletarUnidadeCurricular(1L));
        verify(repository, never()).deleteById(any());
    }

}
