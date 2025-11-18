package com.conselho.api.serviceTesteUnitario;

import com.conselho.api.dto.mapper.UcProfessorMapper;
import com.conselho.api.dto.request.UcProfessorRequestDTO;
import com.conselho.api.dto.response.UcProfessorResponseDTO;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.professor.ProfessorNaoExisteException;
import com.conselho.api.exception.ucProfessor.UcProfessorNaoExisteException;
import com.conselho.api.exception.unidadeCurricular.UnidadeCurricularNaoExisteException;
import com.conselho.api.model.UcProfessor;
import com.conselho.api.model.UnidadeCurricular;
import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.model.entity.Professor;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.UcProfessorRepository;
import com.conselho.api.repository.UnidadeCurricularRepository;
import com.conselho.api.repository.entity.ProfessorRepository;
import com.conselho.api.service.UcProfessorService;
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
class UcProfessorServiceTest {

    @InjectMocks
    private UcProfessorService service;
    @Mock
    private UcProfessorMapper mapper;
    @Mock
    private UcProfessorRepository repository;
    @Mock
    private ConselhoRepository conselhoRepository;
    @Mock
    private ProfessorRepository professorRepository;
    @Mock
    private UnidadeCurricularRepository curricularRepository;

    @Test
    void criarUcProfessor() {
        UcProfessorRequestDTO request = new UcProfessorRequestDTO(1L, 2L, 3L);
        UcProfessor ucProfessor = new UcProfessor();
        UcProfessor salvo = new UcProfessor();
        Conselho conselho = new Conselho();
        Professor professor = new Professor();
        UnidadeCurricular unidadeCurricular = new UnidadeCurricular();
        UcProfessorResponseDTO response = new UcProfessorResponseDTO(1L, 1L, 2L, "henrique", 3L, "Desenvolvimento de Sistemas");

        when(mapper.paraEntidade(request)).thenReturn(ucProfessor);
        when(conselhoRepository.findById(request.idConselho())).thenReturn(Optional.of(conselho));
        when(professorRepository.findById(request.idProfessor())).thenReturn(Optional.of(professor));
        when(curricularRepository.findById(request.idUnidadeCurricular())).thenReturn(Optional.of(unidadeCurricular));
        when(repository.save(ucProfessor)).thenReturn(salvo);
        when(mapper.paraResposta(salvo)).thenReturn(response);

        UcProfessorResponseDTO result = service.criarUcProfessor(request);

        assertEquals(response, result);

        verify(repository).save(ucProfessor);
    }

    @Test
    void criarUcProfessor_ConselhoNaoExiste_DeveLancarExcecao () {
        UcProfessorRequestDTO request = new UcProfessorRequestDTO(1L, 2L, 3L);
        UcProfessor ucProfessor = new UcProfessor();

        when(mapper.paraEntidade(request)).thenReturn(ucProfessor);
        when(conselhoRepository.findById(request.idConselho())).thenReturn(Optional.empty());

        assertThrows(ConselhoNaoExiste.class, () ->{
            service.criarUcProfessor(request);
        });

        verify(repository, never()).save(any());
    }

    @Test
    void criarUcProfessor_ProfessorNaoExisteException_DeveLancarExcecao () {
        UcProfessorRequestDTO request = new UcProfessorRequestDTO(1L, 2L, 3L);
        UcProfessor ucProfessor = new UcProfessor();
        Conselho conselho = new Conselho();

        when(mapper.paraEntidade(request)).thenReturn(ucProfessor);
        when(conselhoRepository.findById(request.idConselho())).thenReturn(Optional.of(conselho));
        when(professorRepository.findById(request.idProfessor())).thenReturn(Optional.empty());

        assertThrows(ProfessorNaoExisteException.class, () ->{
            service.criarUcProfessor(request);
        });

        verify(repository, never()).save(any());
    }

    @Test
    void criarUcProfessor_UnidadeCurricularNaoExisteException_DeveLancarExcecao () {
        UcProfessorRequestDTO request = new UcProfessorRequestDTO(1L, 2L, 3L);
        UcProfessor ucProfessor = new UcProfessor();
        Conselho conselho = new Conselho();
        Professor professor = new Professor();

        when(mapper.paraEntidade(request)).thenReturn(ucProfessor);
        when(conselhoRepository.findById(request.idConselho())).thenReturn(Optional.of(conselho));
        when(professorRepository.findById(request.idProfessor())).thenReturn(Optional.of(professor));
        when(curricularRepository.findById(request.idUnidadeCurricular())).thenReturn(Optional.empty());

        assertThrows(UnidadeCurricularNaoExisteException.class, () ->{
            service.criarUcProfessor(request);
        });

        verify(repository, never()).save(any());
    }

    @Test
    void listarUcProfessor() {
        UcProfessorResponseDTO response = new UcProfessorResponseDTO(1L, 1L, 2L, "henrique", 3L, "Desenvolvimento de Sistemas");
        UcProfessor ucProfessor = new UcProfessor();

        when(repository.findAll()).thenReturn(List.of(ucProfessor));
        when(mapper.paraResposta(ucProfessor)).thenReturn(response);

        List<UcProfessorResponseDTO> result = service.listarUcProfessor();

        assertEquals(1, result.size());
        assertEquals(response, result.get(0));

        verify(repository).findAll();
    }

    @Test
    void buscarUcProfessorPorId() {
        UcProfessorResponseDTO response = new UcProfessorResponseDTO(1L, 1L, 2L, "henrique", 3L, "Desenvolvimento de Sistemas");
        UcProfessor ucProfessor = new UcProfessor();

        when(repository.findById(1L)).thenReturn(Optional.of(ucProfessor));
        when(mapper.paraResposta(ucProfessor)).thenReturn(response);

        UcProfessorResponseDTO result = service.buscarUcProfessorPorId(1L);

        assertEquals(response, result);

        verify(repository).findById(1L);
    }

    @Test
    void buscarUcProfessorPorId_UcProfessorNaoExiste_DeveLancarExcecao () {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UcProfessorNaoExisteException.class, () ->{
            service.buscarUcProfessorPorId(1L);
        });

        verify(repository).findById(1L);
    }

    @Test
    void atualizarUcProfessor() {
        UcProfessorRequestDTO request = new UcProfessorRequestDTO(1L, 2L, 3L);
        UcProfessor existente = new UcProfessor();
        UcProfessor atualizado = new UcProfessor();
        UcProfessorResponseDTO response = new UcProfessorResponseDTO(1L, 1L, 2L, "henrique", 3L, "Desenvolvimento de Sistemas");

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(mapper.paraUpdate(request, existente)).thenReturn(atualizado);
        when(repository.save(atualizado)).thenReturn(atualizado);
        when(mapper.paraResposta(atualizado)).thenReturn(response);

        UcProfessorResponseDTO result = service.atualizarUcProfessor(request, 1L);

        assertEquals(response, result);

        verify(repository).findById(1L);
        verify(mapper).paraUpdate(request, existente);
        verify(repository).save(atualizado);
        verify(mapper).paraResposta(atualizado);
    }

    @Test
    void atualizarUcProfessor_UcProfessorNaoExiste_DeveLancarExcecao () {
        UcProfessorRequestDTO request = new UcProfessorRequestDTO(1L, 2L, 3L);

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UcProfessorNaoExisteException.class, () ->{
            service.atualizarUcProfessor(request,1L);
        });

        verify(repository).findById(1L);
    }

    @Test
    void deletarUcProfessor() {
        when(repository.existsById(1L)).thenReturn(true);

        service.deletarUcProfessor(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void deletarUcProfessor_UcProfessorNaoExiste_DeveLancarExcecao () {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(UcProfessorNaoExisteException.class, ()->{
            service.deletarUcProfessor(1L);
        });

        verify(repository, never()).deleteById(any());
    }
}