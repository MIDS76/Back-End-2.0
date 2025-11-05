package com.conselho.api.testeIntegracao;

import com.conselho.api.dto.request.TurmaRequestDTO;
import com.conselho.api.dto.response.TurmaResponse;
import com.conselho.api.model.Turma;
import com.conselho.api.repository.TurmaRepository;
import com.conselho.api.service.TurmaService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class TurmaTesteIntegracao {
    @Autowired
    private TurmaService turmaService;

    @Autowired
    private TurmaRepository turmaRepository;

    private Turma turma;

    @BeforeEach
    void setup() {
        turma = new Turma();
        turma.setNome("MI-76");
        turmaRepository.save(turma);
    }

    @Test
    void deveCriarTurmaComSucesso() {
        TurmaRequestDTO request = new TurmaRequestDTO("MI-77");

        TurmaResponse response = turmaService.criarTurma(request);

        assertThat(response).isNotNull();
        assertThat(response.id()).isNotNull();
        assertThat(response.nome()).isEqualTo("MI-77");
    }

    @Test
    void deveListarTodasTurmasComSucesso() {
        TurmaRequestDTO request = new TurmaRequestDTO("MI-76");
        turmaService.criarTurma(request);

        List<TurmaResponse> turmas = turmaService.listarTurmas();

        assertThat(turmas).isNotEmpty();
        assertThat(turmas.get(0).nome()).isEqualTo("MI-76");
    }

    @Test
    void deveBuscarTurmaPorIdComSucesso() {
        TurmaRequestDTO request = new TurmaRequestDTO("MI-76");
        TurmaResponse createdTurma = turmaService.criarTurma(request);

        TurmaResponse response = turmaService.buscarTurmaPorId(createdTurma.id());

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(createdTurma.id());
        assertThat(response.nome()).isEqualTo("MI-76");
    }

    @Test
    void deveAtualizarTurmaComSucesso() {
        TurmaRequestDTO request = new TurmaRequestDTO("MI-76");
        TurmaResponse createdTurma = turmaService.criarTurma(request);

        TurmaRequestDTO updatedRequest = new TurmaRequestDTO("MI-78");

        TurmaResponse updatedTurma = turmaService.atualizarTurma(createdTurma.id(), updatedRequest);

        assertThat(updatedTurma).isNotNull();
        assertThat(updatedTurma.nome()).isEqualTo("MI-78");
    }

    @Test
    void deveDeletarTurmaComSucesso() {
        TurmaRequestDTO request = new TurmaRequestDTO("MI-76");
        TurmaResponse createdTurma = turmaService.criarTurma(request);

        turmaService.deletarTurma(createdTurma.id());

        assertThat(turmaRepository.findById(createdTurma.id())).isEmpty();
    }
}
