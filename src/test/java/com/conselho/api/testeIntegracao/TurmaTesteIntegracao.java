//package com.conselho.api.testeIntegracao;
//
//import com.conselho.api.dto.request.TurmaRequestDTO;
//import com.conselho.api.dto.response.TurmaResponseDTO;
//import com.conselho.api.model.entity.Turma;
//import com.conselho.api.repository.TurmaRepository;
//import com.conselho.api.service.TurmaService;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestConstructor;
//
//import java.util.List;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@SpringBootTest
//@Transactional
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
//public class TurmaTesteIntegracao {
//    private final TurmaService turmaService;
//    private final TurmaRepository turmaRepository;
//
//    public TurmaTesteIntegracao(
//            TurmaService turmaService,
//            TurmaRepository turmaRepository
//    ){
//        this.turmaService = turmaService;
//        this.turmaRepository = turmaRepository;
//    }
//
//    private Turma turma;
//
//    @BeforeEach
//    void setup() {
//        turma = new Turma();
//        turma.setNome("MI-76");
//        turmaRepository.save(turma);
//    }
//
//    @Test
//    void deveCriarTurmaComSucesso() {
//        TurmaRequestDTO request = new TurmaRequestDTO("MI-77", "TI", 2025-11-23);
//        TurmaResponseDTO response = turmaService.criarTurma(request);
//
//        assertThat(response).isNotNull();
//        assertThat(response.id()).isNotNull();
//        assertThat(turmaRepository.findById(response.id())).isPresent();
//        assertThat(response.nome()).isEqualTo("MI-77");
//    }
//
//    @Test
//    void deveListarTodasTurmasComSucesso() {
//        TurmaRequestDTO request = new TurmaRequestDTO("MI-76", "TI", 2026-11-23);
//        turmaService.criarTurma(request);
//
//        List<TurmaResponseDTO> turmas = turmaService.listarTurmas();
//
//        assertThat(turmas).isEqualTo(request.curso());
//        assertThat(turmas.get(0).nome()).isEqualTo("MI-76");
//    }
//
//    @Test
//    void deveBuscarTurmaPorIdComSucesso() {
//        TurmaRequestDTO request = new TurmaRequestDTO("MI-76", "TI", 2026-10-13);
//        TurmaResponseDTO createdTurma = turmaService.criarTurma(request);
//
//        TurmaResponseDTO response = turmaService.buscarTurmaPorId(createdTurma.id());
//
//        assertThat(response).isNotNull();
//        assertThat(response.id()).isEqualTo(createdTurma.id());
//        assertThat(response.nome()).isEqualTo("MI-76");
//    }
//
//    @Test
//    void deveAtualizarTurmaComSucesso() {
//        TurmaRequestDTO request = new TurmaRequestDTO("MI-76", "TI",2025-10-20);
//        TurmaResponseDTO createdTurma = turmaService.criarTurma(request);
//
//        TurmaRequestDTO updatedRequest = new TurmaRequestDTO("MI-78", "TI", 2025-11-21);
//
//        TurmaResponseDTO updatedTurma = turmaService.atualizarTurma(createdTurma.id(), updatedRequest);
//
//        assertThat(updatedTurma).isNotNull();
//        assertThat(updatedTurma.nome()).isEqualTo("MI-78");
//    }
//
//    @Test
//    void deveDeletarTurmaComSucesso() {
//        TurmaRequestDTO request = new TurmaRequestDTO("MI-76", "TI", 2025-11-19);
//        TurmaResponseDTO createdTurma = turmaService.criarTurma(request);
//
//        turmaService.deletarTurma(createdTurma.id());
//
//        assertThat(turmaRepository.findById(createdTurma.id())).isEmpty();
//    }
//}
