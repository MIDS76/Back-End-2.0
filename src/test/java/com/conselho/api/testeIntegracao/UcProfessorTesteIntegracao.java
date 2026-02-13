package com.conselho.api.testeIntegracao;

import com.conselho.api.dto.request.UcProfessorRequestDTO;
import com.conselho.api.dto.response.UcProfessorResponseDTO;
import com.conselho.api.model.UcProfessor;
import com.conselho.api.repository.UcProfessorRepository;
import com.conselho.api.service.UcProfessorService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class UcProfessorTesteIntegracao {
    private final UcProfessorService ucProfessorService;
    private final UcProfessorRepository ucProfessorRepository;
    private UcProfessor ucProfessor;

    public UcProfessorTesteIntegracao (
            UcProfessorService ucProfessorService,
            UcProfessorRepository ucProfessorRepository
    ) {
        this.ucProfessorService = ucProfessorService;
        this.ucProfessorRepository = ucProfessorRepository;
    }

    @BeforeEach
    void setup() {
        UcProfessor ucProfessor = new UcProfessor();

        ucProfessor.setId(1L);
        ucProfessorRepository.save(ucProfessor);
    }

    @Test
    void deveCriarUcProfessorComSucesso() {
        UcProfessorRequestDTO requestDTO = new UcProfessorRequestDTO(1L, 6L, 2L);
        UcProfessorResponseDTO responseDTO = ucProfessorService.criarUcProfessor(requestDTO);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.id()).isNotNull();
        assertThat(responseDTO.idProfessor()).isNotNull();
        assertThat(responseDTO.idConselho()).isNotNull();
        assertThat(responseDTO.idUnidadeCurricular()).isNotNull();
    }

    @Test
    void deveListarUcProfessorComSucesso() {
        UcProfessorRequestDTO requestDTO = new UcProfessorRequestDTO(1L,6L,2L);
        ucProfessorService.criarUcProfessor(requestDTO);
        List<UcProfessorResponseDTO> ucProfessor = ucProfessorService.listarUcProfessor();

        assertThat(ucProfessor).isNotNull();
        assertThat(ucProfessor.get(1)).isNotNull();
    }

    @Test
    void deveBuscarUcProfessorPorIdComSucesso() {
        UcProfessorRequestDTO requestDTO = new UcProfessorRequestDTO(1L,5L,2L);
        UcProfessorResponseDTO responseDTO = ucProfessorService.criarUcProfessor(requestDTO);
        UcProfessorResponseDTO response = ucProfessorService.buscarUcProfessorPorId(responseDTO.id());

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(responseDTO.id());
        assertThat(response.idProfessor()).isEqualTo(responseDTO.idProfessor());
        assertThat(response.idConselho()).isEqualTo(responseDTO.idConselho());
        assertThat(response.idUnidadeCurricular()).isEqualTo(responseDTO.idUnidadeCurricular());
    }

    @Test
    void deveAtualizarUcProfessorComSucesso() {
        UcProfessorRequestDTO requestDTO = new UcProfessorRequestDTO(7L,6L,2L);
        UcProfessorResponseDTO responseDTO = ucProfessorService.criarUcProfessor(requestDTO);
        UcProfessorResponseDTO response = ucProfessorService.atualizarUcProfessor(requestDTO, responseDTO.id());

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(7);
    }

//    @Test
//    void deveDeletarUcProfessor() {
//        UcProfessorRequestDTO requestDTO = new UcProfessorRequestDTO(1L, 6L, 2L);
//        UcProfessorResponseDTO responseDTO = ucProfessorService.criarUcProfessor(requestDTO);
//        ucProfessorService.deletarUcProfessor(responseDTO.id());
//
//        assertThat(ucProfessorRepository.findById(responseDTO.id())).isEmpty();
//    }

}
