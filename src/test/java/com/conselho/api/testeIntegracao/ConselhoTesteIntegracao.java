//package com.conselho.api.testeIntegracao;
//
//import com.conselho.api.dto.request.ConselhoRequestDTO;
//import com.conselho.api.dto.response.ConselhoResponseDTO;
//import static org.assertj.core.api.Assertions.assertThat;
//import com.conselho.api.model.entity.Aluno;
//import com.conselho.api.model.entity.Pedagogico;
//import com.conselho.api.model.entity.Turma;
//import com.conselho.api.repository.ConselhoRepository;
//import com.conselho.api.repository.TurmaRepository;
//import com.conselho.api.repository.entity.AlunoRepository;
//import com.conselho.api.repository.entity.PedagogicoRepository;
//import com.conselho.api.service.ConselhoService;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestConstructor;
//import java.time.LocalDate;
//import java.util.List;
//
//
//@SpringBootTest
//@Transactional
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
//public class ConselhoTesteIntegracao {
//
//    private ConselhoService conselhoService;
//    private ConselhoRepository conselhoRepository;
//    private TurmaRepository turmaRepository;
//    private AlunoRepository alunoRepository;
//    private PedagogicoRepository pedagogicoRepository;
//
//    public ConselhoTesteIntegracao (
//            ConselhoService conselhoService,
//            ConselhoRepository conselhoRepository,
//            TurmaRepository turmaRepository,
//            AlunoRepository alunoRepository,
//            PedagogicoRepository pedagogicoRepository
//    ) {
//        this.conselhoService = conselhoService;
//        this.conselhoRepository = conselhoRepository;
//        this.turmaRepository = turmaRepository;
//        this.alunoRepository = alunoRepository;
//        this.pedagogicoRepository = pedagogicoRepository;
//    }
//    private Turma turma;
//    private Aluno aluno1, aluno2;
//    private Pedagogico pedagogico;
//
//    @BeforeEach
//    void setup() {
//        turma = new Turma();
//        turma.setNome("Turma de Teste");
//        turmaRepository.save(turma);
//
//        aluno1 = new Aluno();
//        aluno1.setNome("Raquel");
//        aluno1.setEmail("raquel@gmail.com");
//        alunoRepository.save(aluno1);
//
//        aluno2 = new Aluno();
//        aluno2.setNome("Julia");
//        aluno2.setEmail("julia@gmail.com");
//        alunoRepository.save(aluno2);
//
//        pedagogico = new Pedagogico();
//        pedagogico.setNome("Pedagógico Teste");
//        pedagogicoRepository.save(pedagogico);
//    }
//
//    @Test
//    void deveCriarConselhoComSucesso() {
//        LocalDate dataInicio = LocalDate.now();
//        LocalDate dataFim = LocalDate.now().plusMonths(1);
//
//        ConselhoRequestDTO request = new ConselhoRequestDTO(
//                turma.getId(),
//                dataInicio,
//                dataFim,
//                aluno1.getId(),
//                aluno2.getId(),
//                pedagogico.getId()
//        );
//        ConselhoResponseDTO response = conselhoService.criarConselho(request);
//
//        assertThat(response).isNotNull();
//        assertThat(response.id()).isNotNull();
//        assertThat(response.idRepresentante1()).isEqualTo(aluno1.getId());
//        assertThat(response.idRepresentante2()).isEqualTo(aluno2.getId());
//        assertThat(response.idPedagogico()).isEqualTo(pedagogico.getId());
//    }
//    @Test
//    void deveListarTodosConselhos() {
//        LocalDate dataInicio = LocalDate.now();
//        LocalDate dataFim = LocalDate.now().plusMonths(1);
//
//        ConselhoRequestDTO request = new ConselhoRequestDTO(
//                turma.getId(),
//                dataInicio,
//                dataFim,
//                aluno1.getId(),
//                aluno2.getId(),
//                pedagogico.getId()
//        );
//        conselhoService.criarConselho(request);
//
//        List<ConselhoResponseDTO> response = conselhoService.listarConselhos();
//        assertThat(response).isNotEmpty();
//        assertThat(response.get(0).id()).isNotNull();
//    }
//
//    @Test
//    void deveBuscarConselhoPorId() {
//        LocalDate dataInicio = LocalDate.now();
//        LocalDate dataFim = LocalDate.now().plusMonths(1);
//
//        ConselhoRequestDTO request = new ConselhoRequestDTO(
//                turma.getId(),
//                dataInicio,
//                dataFim,
//                aluno1.getId(),
//                aluno2.getId(),
//                pedagogico.getId()
//        );
//
//        ConselhoResponseDTO createdConselho = conselhoService.criarConselho(request);
//        ConselhoResponseDTO response = conselhoService.buscarConselhoPorId(createdConselho.id());
//
//        assertThat(response).isNotNull();
//        assertThat(response.id()).isEqualTo(createdConselho.id());
//    }
//
//
//    @Test
//    void deveAtualizarConselhoComSucesso() {
//        LocalDate dataInicio = LocalDate.now();
//        LocalDate dataFim = LocalDate.now().plusMonths(1);
//
//        ConselhoRequestDTO request = new ConselhoRequestDTO(
//                turma.getId(),
//                dataInicio,
//                dataFim,
//                aluno1.getId(),
//                aluno2.getId(),
//                pedagogico.getId()
//        );
//
//        ConselhoResponseDTO createdConselho = conselhoService.criarConselho(request);
//
//        LocalDate updatedDataInicio = LocalDate.now().plusDays(1);
//        LocalDate updatedDataFim = LocalDate.now().plusMonths(2);
//
//        ConselhoRequestDTO updatedRequest = new ConselhoRequestDTO(
//                turma.getId(),
//                updatedDataInicio,
//                updatedDataFim,
//                aluno2.getId(),
//                aluno1.getId(),
//                pedagogico.getId()
//        );
//
//        ConselhoResponseDTO updatedConselho = conselhoService.atualizarConselho(createdConselho.id(), updatedRequest);
//
//        assertThat(updatedConselho).isNotNull();
//        assertThat(updatedConselho.idRepresentante1()).isEqualTo(aluno2.getId());
//        assertThat(updatedConselho.idRepresentante2()).isEqualTo(aluno1.getId());
//    }
//
//
//    @Test
//    void deveDeletarConselhoComSucesso() {
//        LocalDate dataInicio = LocalDate.now();
//        LocalDate dataFim = LocalDate.now().plusMonths(1);
//
//        ConselhoRequestDTO request = new ConselhoRequestDTO(
//                turma.getId(),
//                dataInicio,
//                dataFim,
//                aluno1.getId(),
//                aluno2.getId(),
//                pedagogico.getId()
//        );
//
//        ConselhoResponseDTO createdConselho = conselhoService.criarConselho(request);
//
//        conselhoService.deletarConselho(createdConselho.id());
//
//        assertThat(conselhoRepository.findById(createdConselho.id())).isEmpty();
//    }
//
//}
