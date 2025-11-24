package com.conselho.api.service;

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
        UcProfessorRequestDTO request =
                new UcProfessorRequestDTO(1L, 2L, List.of(2L));

        Conselho conselho = new Conselho();
        conselho.setId(1L);

        Professor professor = new Professor();
        professor.setId(2L);
        professor.setNome("henrique");

        UnidadeCurricular uc = new UnidadeCurricular();
        uc.setId(2L);

        UcProfessor ucProfessor = new UcProfessor();
        ucProfessor.setConselho(conselho);
        ucProfessor.setProfessor(professor);
        ucProfessor.setUnidadeCurricular(uc);

        List<UcProfessor> listaSalva = List.of(ucProfessor);

        UcProfessorResponseDTO response =
                new UcProfessorResponseDTO(
                        1L,
                        1L,
                        2L,
                        "henrique",
                        List.of("Desenvolvimento de Sistemas")
                );

        when(conselhoRepository.findById(1L)).thenReturn(Optional.of(conselho));
        when(professorRepository.findById(2L)).thenReturn(Optional.of(professor));
        when(curricularRepository.findByIdIn(List.of(2L)))
                .thenReturn(List.of(uc));
        when(curricularRepository.findNomesByIds(List.of(2L)))
                .thenReturn(List.of("Desenvolvimento de Sistemas"));

        when(mapper.paraRespostaComLista(ucProfessor, List.of("Desenvolvimento de Sistemas")))
                .thenReturn(response);

        UcProfessorResponseDTO result = service.criarUcProfessor(request);

        assertEquals(response, result);

        verify(repository).saveAll(anyList());
    }


    @Test
    void criarUcProfessor_ConselhoNaoExiste_DeveLancarExcecao () {
        UcProfessorRequestDTO request =
                new UcProfessorRequestDTO(1L, 2L, List.of(3L));

        when(conselhoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ConselhoNaoExiste.class, () -> {
            service.criarUcProfessor(request);
        });

        verify(repository, never()).saveAll(any());
    }

    @Test
    void criarUcProfessor_ProfessorNaoExisteException_DeveLancarExcecao () {
        UcProfessorRequestDTO request =
                new UcProfessorRequestDTO(1L, 2L, List.of(3L));

        Conselho conselho = new Conselho();

        when(conselhoRepository.findById(1L)).thenReturn(Optional.of(conselho));
        when(professorRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ProfessorNaoExisteException.class, () -> {
            service.criarUcProfessor(request);
        });

        verify(repository, never()).saveAll(any());
    }

    @Test
    void criarUcProfessor_UnidadeCurricularNaoExisteException_DeveLancarExcecao () {
        UcProfessorRequestDTO request =
                new UcProfessorRequestDTO(1L, 2L, List.of(3L));

        Conselho conselho = new Conselho();
        Professor professor = new Professor();

        when(conselhoRepository.findById(1L)).thenReturn(Optional.of(conselho));
        when(professorRepository.findById(2L)).thenReturn(Optional.of(professor));
        when(curricularRepository.findByIdIn(List.of(3L)))
                .thenReturn(List.of());

        assertThrows(UnidadeCurricularNaoExisteException.class, () -> {
            service.criarUcProfessor(request);
        });

        verify(repository, never()).saveAll(any());
    }

    @Test
    void listarUcProfessor() {
        UnidadeCurricular uc = new UnidadeCurricular();
        uc.setId(1L);

        UcProfessor ucProfessor = new UcProfessor();
        ucProfessor.setUnidadeCurricular(uc);

        UcProfessorResponseDTO response = new UcProfessorResponseDTO(
                1L, 1L, 2L, "henrique", List.of("Desenvolvimento de Sistemas")
        );

        when(repository.findAll()).thenReturn(List.of(ucProfessor));
        when(curricularRepository.findNomesByIds(List.of(1L)))
                .thenReturn(List.of("Desenvolvimento de Sistemas"));

        when(mapper.paraRespostaComLista(ucProfessor, List.of("Desenvolvimento de Sistemas")))
                .thenReturn(response);

        List<UcProfessorResponseDTO> result = service.listarUcProfessor();

        assertEquals(1, result.size());
        assertEquals(response, result.get(0));

        verify(repository).findAll();
    }

    @Test
    void buscarUcProfessorPorId() {
        UcProfessorResponseDTO response = new UcProfessorResponseDTO(
                1L,
                1L,
                2L,
                "henrique",
                List.of("Desenvolvimento de Sistemas")
        );

        UcProfessor ucProfessor = new UcProfessor();

        Conselho conselho = new Conselho();
        conselho.setId(1L);

        Professor professor = new Professor();
        professor.setId(2L);
        professor.setNome("henrique");

        UnidadeCurricular uc = new UnidadeCurricular();
        uc.setId(3L);
        uc.setNome("Desenvolvimento de Sistemas");

        ucProfessor.setConselho(conselho);
        ucProfessor.setProfessor(professor);
        ucProfessor.setUnidadeCurricular(uc);

        when(repository.findById(1L)).thenReturn(Optional.of(ucProfessor));

        when(curricularRepository.findNomesByIds(any()))
                .thenReturn(List.of("Desenvolvimento de Sistemas"));

        when(mapper.paraRespostaComLista(any(), any()))
                .thenReturn(response);

        UcProfessorResponseDTO result = service.buscarUcProfessorPorId(1L);

        assertEquals(response, result);
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
        UcProfessorRequestDTO request = new UcProfessorRequestDTO(1L, 2L,  List.of(3L));

        UcProfessor existente = new UcProfessor();
        UcProfessor atualizado = new UcProfessor();

        UcProfessorResponseDTO response =
                new UcProfessorResponseDTO(1L, 1L, 2L, "henrique", List.of("Desenvolvimento de Sistemas"));

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(mapper.paraUpdate(any(), any())).thenReturn(atualizado);
        when(repository.save(any())).thenReturn(atualizado);
        when(mapper.paraResposta(any())).thenReturn(response);

        UcProfessorResponseDTO result = service.atualizarUcProfessor(request, 1L);

        assertEquals(response, result);

        verify(repository).findById(1L);
        verify(mapper).paraUpdate(request, existente);
        verify(repository).save(atualizado);
        verify(mapper).paraResposta(atualizado);
    }

    @Test
    void atualizarUcProfessor_UcProfessorNaoExiste_DeveLancarExcecao () {
        UcProfessorRequestDTO request = new UcProfessorRequestDTO(1L, 2L, List.of(3L));

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