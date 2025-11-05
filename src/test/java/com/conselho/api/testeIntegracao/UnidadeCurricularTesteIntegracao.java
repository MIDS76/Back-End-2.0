package com.conselho.api.testeIntegracao;

import com.conselho.api.dto.request.UnidadeCurricularRequestDTO;
import com.conselho.api.dto.response.UnidadeCurricularResponse;
import com.conselho.api.exception.unidadeCurricular.UnidadeCurricularExisteException;
import com.conselho.api.model.unidadeCurricular.UnidadeCurricular;
import com.conselho.api.repository.UnidadeCurricularRepository;
import com.conselho.api.service.UnidadeCurricularService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class UnidadeCurricularTesteIntegracao {
    @Autowired
    private UnidadeCurricularService unidadeCurricularService;

    @Autowired
    private UnidadeCurricularRepository unidadeCurricularRepository;

    @BeforeEach
    void setup() {
        UnidadeCurricular unidadeCurricular = new UnidadeCurricular();
        unidadeCurricular.setNome("Banco de Dados");
        unidadeCurricularRepository.save(unidadeCurricular);
    }

    @Test
    void deveCriarUnidadeCurricularComSucesso() {
        UnidadeCurricularRequestDTO request = new UnidadeCurricularRequestDTO("Analise de sistemas");

        UnidadeCurricularResponse response = unidadeCurricularService.criarUnidadeCurricular(request);

        assertThat(response).isNotNull();
        assertThat(response.id()).isNotNull();
        assertThat(response.nome()).isEqualTo("Analise de sistemas");
    }

    @Test
    void deveListarUnidadesCurricularesComSucesso() {
        UnidadeCurricularRequestDTO request = new UnidadeCurricularRequestDTO("Flutter");
        unidadeCurricularService.criarUnidadeCurricular(request);

        List<UnidadeCurricularResponse> unidades = unidadeCurricularService.listarUnidadesCurriculares();

        assertThat(unidades).isNotEmpty();
        assertThat(unidades.get(0).nome()).isEqualTo("Banco de Dados");
    }

    @Test
    void deveBuscarUnidadeCurricularPorIdComSucesso() {
        UnidadeCurricularRequestDTO request = new UnidadeCurricularRequestDTO("Documentação de sistemas");
        UnidadeCurricularResponse createdUnidade = unidadeCurricularService.criarUnidadeCurricular(request);

        UnidadeCurricularResponse response = unidadeCurricularService.buscarUnidadesPorId(createdUnidade.id());

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(createdUnidade.id());
        assertThat(response.nome()).isEqualTo("Documentação de sistemas");
    }

    @Test
    void deveAtualizarUnidadeCurricularComSucesso() {
        UnidadeCurricularRequestDTO request = new UnidadeCurricularRequestDTO("Data Science");
        UnidadeCurricularResponse createdUnidade = unidadeCurricularService.criarUnidadeCurricular(request);

        UnidadeCurricularRequestDTO updatedRequest = new UnidadeCurricularRequestDTO("Programação de API");

        UnidadeCurricularResponse updatedUnidade = unidadeCurricularService.atualizarUnidadeCurricular(createdUnidade.id(), updatedRequest);

        assertThat(updatedUnidade).isNotNull();
        assertThat(updatedUnidade.nome()).isEqualTo("Programação de API");
    }

    @Test
    void deveDeletarUnidadeCurricularComSucesso() {
        UnidadeCurricularRequestDTO request = new UnidadeCurricularRequestDTO("Front-End");
        UnidadeCurricularResponse createdUnidade = unidadeCurricularService.criarUnidadeCurricular(request);
        unidadeCurricularService.deletarUnidadeCurricular(createdUnidade.id());

        assertThat(unidadeCurricularRepository.findById(createdUnidade.id())).isEmpty();
    }

    @Test
    void naoDeveCriarUnidadeCurricularComNomeExistente() {
        UnidadeCurricularRequestDTO request = new UnidadeCurricularRequestDTO("Banco de Dados");

        unidadeCurricularService.criarUnidadeCurricular(request);

        org.junit.jupiter.api.Assertions.assertThrows(UnidadeCurricularExisteException.class, () ->
                unidadeCurricularService.criarUnidadeCurricular(request)
        );
    }

    @Test
    void deveProcessarJsonComSucesso() throws IOException {
        String json = "[{\"nome\": \"Física\"}, {\"nome\": \"Química\"}]";
        MockMultipartFile file = new MockMultipartFile("file", "unidades.json", "application/json", json.getBytes());

        unidadeCurricularService.processarJson(file);

        List<UnidadeCurricular> unidades = unidadeCurricularRepository.findAll();

        assertThat(unidades).hasSize(3);
        assertThat(unidades.stream().anyMatch(u -> u.getNome().equals("Física"))).isTrue();
        assertThat(unidades.stream().anyMatch(u -> u.getNome().equals("Química"))).isTrue();
    }
}
