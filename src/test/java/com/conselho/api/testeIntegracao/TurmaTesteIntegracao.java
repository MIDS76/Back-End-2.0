package com.conselho.api.testeIntegracao;

import com.conselho.api.dto.request.TurmaRequestDTO;
import com.conselho.api.dto.response.TurmaResponseDTO;
import com.conselho.api.model.Turma;
import com.conselho.api.repository.TurmaRepository;
import com.conselho.api.service.TurmaService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class TurmaTesteIntegracao {
    private final TurmaService turmaService;
    private final TurmaRepository turmaRepository;

    public TurmaTesteIntegracao(
            TurmaService turmaService,
            TurmaRepository turmaRepository
    ){
        this.turmaService = turmaService;
        this.turmaRepository = turmaRepository;
    }
    private Turma turma;

    @BeforeEach
    void setup() {
        turma = new Turma();
        turma.setNome("mi-76");
        turma.setCurso("TI");
        turma.setDataInicio(LocalDate.of(2025,11,12));
        turma.setDataFim(LocalDate.of(2025,11,24));
        turmaRepository.save(turma);
    }

    @Test
    void deveCriarTurmaComSucesso() {
        TurmaRequestDTO request = new TurmaRequestDTO("MI-77", "TI", LocalDate.of(2025,11,19),LocalDate.of(2025,11,25));
        TurmaResponseDTO response = turmaService.criarTurma(request);

        assertThat(response).isNotNull();
        assertThat(response.id()).isNotNull();
        assertThat(turmaRepository.findById(response.id())).isPresent();
        assertThat(response.nome()).isEqualTo("MI-77");
    }

    @Test
    void deveListarTodasTurmasComSucesso() {
        TurmaRequestDTO request = new TurmaRequestDTO("mi-76", "TI", LocalDate.of(2025, 11, 19), LocalDate.of(2025, 11, 25));
        turmaService.criarTurma(request);
        List<TurmaResponseDTO> turmas = turmaService.listarTurmas();

        assertThat(turmas.stream().anyMatch(turma -> "TI".equals(turma.curso()))).isTrue();
        assertThat(turmas.get(0).nome()).isEqualTo("mi-76");
    }


    @Test
    void deveBuscarTurmaPorIdComSucesso() {
        TurmaRequestDTO request = new TurmaRequestDTO("mi-76", "TI", LocalDate.of(2025,11,19),LocalDate.of(2025,11,25));
        TurmaResponseDTO createdTurma = turmaService.criarTurma(request);

        TurmaResponseDTO response = turmaService.buscarTurmaPorId(createdTurma.id());

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(createdTurma.id());
        assertThat(response.nome()).isEqualTo("mi-76");
    }

    @Test
    void deveAtualizarTurmaComSucesso() {
        TurmaRequestDTO request = new TurmaRequestDTO("mi-76", "TI",LocalDate.of(2025,11,19),LocalDate.of(2025,11,25));
        TurmaResponseDTO createdTurma = turmaService.criarTurma(request);

        TurmaRequestDTO updatedRequest = new TurmaRequestDTO("MI-78", "TI", LocalDate.of(2025,11,19),LocalDate.of(2025,11,25));

        TurmaResponseDTO updatedTurma = turmaService.atualizarTurma(createdTurma.id(), updatedRequest);

        assertThat(updatedTurma).isNotNull();
        assertThat(updatedTurma.nome()).isEqualTo("MI-78");
    }

    @Test
    void deveDeletarTurmaComSucesso() {
        TurmaRequestDTO request = new TurmaRequestDTO("mi-76", "TI", LocalDate.of(2025,11,19),LocalDate.of(2025,11,25));
        TurmaResponseDTO createdTurma = turmaService.criarTurma(request);

        turmaService.deletarTurma(createdTurma.id());

        assertThat(turmaRepository.findById(createdTurma.id())).isEmpty();
    }
}
