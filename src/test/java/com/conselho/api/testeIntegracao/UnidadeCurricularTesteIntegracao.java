package com.conselho.api.testeIntegracao;

import com.conselho.api.dto.request.UnidadeCurricularRequestDTO;
import com.conselho.api.dto.response.UnidadeCurricularResponseDTO;
import com.conselho.api.exception.unidadeCurricular.UnidadeCurricularExisteException;
import com.conselho.api.model.UnidadeCurricular;
import com.conselho.api.repository.UnidadeCurricularRepository;
import com.conselho.api.service.UnidadeCurricularService;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class UnidadeCurricularTesteIntegracao {
    private final UnidadeCurricularService unidadeCurricularService;
    private final UnidadeCurricularRepository unidadeCurricularRepository;

    public UnidadeCurricularTesteIntegracao (
            UnidadeCurricularService unidadeCurricularService,
            UnidadeCurricularRepository unidadeCurricularRepository
    ){
        this.unidadeCurricularService = unidadeCurricularService;
        this.unidadeCurricularRepository = unidadeCurricularRepository;
    }
    @BeforeEach
    void setup() {
        UnidadeCurricular unidadeCurricular = new UnidadeCurricular();

        unidadeCurricular.setNome("Banco de Dados");
        unidadeCurricularRepository.save(unidadeCurricular);
    }

    @Test
    void deveCriarUnidadeCurricularComSucesso() {
        UnidadeCurricularRequestDTO request = new UnidadeCurricularRequestDTO("Analise de sistemas");

        UnidadeCurricularResponseDTO response = unidadeCurricularService.criarUnidadeCurricular(request);

        assertThat(response).isNotNull();
        assertThat(response.id()).isNotNull();
        assertThat(response.nome()).isEqualTo("Analise de sistemas");
    }

    @Test
    void deveListarUnidadesCurricularesComSucesso() {
        var unidadeCurricular = unidadeCurricularService.listarUnidadesCurriculares();

        assertThat(unidadeCurricular).isNotNull();
    }

    @Test
    void deveBuscarUnidadeCurricularPorIdComSucesso() {
        UnidadeCurricularRequestDTO request = new UnidadeCurricularRequestDTO("Documentação de sistemas");
        UnidadeCurricularResponseDTO createdUnidade = unidadeCurricularService.criarUnidadeCurricular(request);

        UnidadeCurricularResponseDTO response = unidadeCurricularService.buscarUnidadesPorId(createdUnidade.id());

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(createdUnidade.id());
        assertThat(response.nome()).isEqualTo("Documentação de sistemas");
    }

    @Test
    void deveAtualizarUnidadeCurricularComSucesso() {
        UnidadeCurricularRequestDTO request = new UnidadeCurricularRequestDTO("Data Science");
        UnidadeCurricularResponseDTO createdUnidade = unidadeCurricularService.criarUnidadeCurricular(request);

        UnidadeCurricularRequestDTO updatedRequest = new UnidadeCurricularRequestDTO("Programação de API");

        UnidadeCurricularResponseDTO updatedUnidade = unidadeCurricularService.atualizarUnidadeCurricular(createdUnidade.id(), updatedRequest);

        assertThat(updatedUnidade).isNotNull();
        assertThat(updatedUnidade.nome()).isEqualTo("Programação de API");
    }

    @Test
    void deveDeletarUnidadeCurricularComSucesso() {
        UnidadeCurricularRequestDTO request = new UnidadeCurricularRequestDTO("Front-End");
        UnidadeCurricularResponseDTO createdUnidade = unidadeCurricularService.criarUnidadeCurricular(request);
        unidadeCurricularService.deletarUnidadeCurricular(createdUnidade.id());

        assertThat(unidadeCurricularRepository.findById(createdUnidade.id())).isEmpty();
    }

    @Test
    void naoDeveCriarUnidadeCurricularComNomeExistente() {
        UnidadeCurricularRequestDTO request = new UnidadeCurricularRequestDTO("Automação Industrial");

        unidadeCurricularService.criarUnidadeCurricular(request);

        org.junit.jupiter.api.Assertions.assertThrows(UnidadeCurricularExisteException.class, () ->
                unidadeCurricularService.criarUnidadeCurricular(request)
        );
    }
}
