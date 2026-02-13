//package com.conselho.api.testeIntegracao.feedback;
//
//import com.conselho.api.dto.request.feedback.ConselhoAlunoFeedbackRequestDTO;
//import com.conselho.api.dto.response.feedback.ConselhoAlunoFeedbackResponseDTO;
//import com.conselho.api.model.conselho.Conselho;
//import com.conselho.api.model.entity.Aluno;
//import com.conselho.api.model.entity.Pedagogico;
//import com.conselho.api.model.feedback.ConselhoAlunoFeedback;
//import com.conselho.api.repository.ConselhoRepository;
//import com.conselho.api.repository.entity.AlunoRepository;
//import com.conselho.api.repository.entity.PedagogicoRepository;
//import com.conselho.api.repository.feedback.ConselhoAlunoFeedbackRepository;
//import com.conselho.api.service.feedback.ConselhoAlunoFeedbackService;
//import jakarta.transaction.Transactional;
//import org.assertj.core.api.AssertionsForClassTypes;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestConstructor;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@Transactional
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
//public class ConselhoAlunoFeedbackTesteIntegracao {
//
//    private final ConselhoAlunoFeedbackService conselhoAlunoFeedbackService;
//    private final ConselhoAlunoFeedbackRepository conselhoAlunoFeedbackRepository;
//    private final AlunoRepository alunoRepository;
//    private final ConselhoRepository conselhoRepository;
//    private final PedagogicoRepository pedagogicoRepository;
//
//    public ConselhoAlunoFeedbackTesteIntegracao(
//            ConselhoAlunoFeedbackService conselhoAlunoFeedbackService,
//            ConselhoAlunoFeedbackRepository conselhoAlunoFeedbackRepository,
//            AlunoRepository alunoRepository,
//            ConselhoRepository conselhoRepository,
//            PedagogicoRepository pedagogicoRepository
//    ) {
//        this.conselhoAlunoFeedbackService = conselhoAlunoFeedbackService;
//        this.conselhoAlunoFeedbackRepository = conselhoAlunoFeedbackRepository;
//        this.alunoRepository = alunoRepository;
//        this.conselhoRepository = conselhoRepository;
//        this.pedagogicoRepository = pedagogicoRepository;
//    }
//
//    private Aluno alunoCriado;
//    private Conselho conselhoCriado;
//    private Pedagogico pedagogicoCriado;
//
//    @BeforeEach
//    void setup() {
//
//        // Criar aluno
//        Aluno aluno = new Aluno();
//        aluno.setNome("Aluno Teste");
//        alunoCriado = alunoRepository.save(aluno);
//
//        // Criar conselho
//        Conselho conselho = new Conselho();
//        conselhoCriado = conselhoRepository.save(conselho);
//
//        // Criar pedagógico
//        Pedagogico pedagogico = new Pedagogico();
//        pedagogico.setNome("Pedagógico Teste");
//        pedagogicoCriado = pedagogicoRepository.save(pedagogico);
//
//        // Criar feedback inicial
//        ConselhoAlunoFeedback feedback = new ConselhoAlunoFeedback();
//        feedback.setAluno(alunoCriado);
//        feedback.setConselho(conselhoCriado);
//        feedback.setPedagogico(pedagogicoCriado);
//        feedback.setPontosPositivos("Comunicativo");
//        feedback.setPontosMelhoria("Menos conversa");
//        feedback.setSugestao("Focar mais no aprendizado");
//
//        conselhoAlunoFeedbackRepository.save(feedback);
//    }
//
//    @Test
//    void deveListarConselhoAlunoFeedback() {
//
//        ConselhoAlunoFeedbackRequestDTO requestDTO =
//                new ConselhoAlunoFeedbackRequestDTO(
//                        alunoCriado.getId(),
//                        conselhoCriado.getId(),
//                        pedagogicoCriado.getId(),
//                        "Comunicativo",
//                        "Menas conversa",
//                        "focar mais em front end"
//                );
//
//        conselhoAlunoFeedbackService.create(requestDTO);
//
//        List<ConselhoAlunoFeedbackResponseDTO> lista =
//                conselhoAlunoFeedbackService.buscarTodos();
//
//        assertThat(lista).isNotEmpty();
//    }
//
//    @Test
//    void deveAtualizarUnidadeCurricularComSucesso() {
//        ConselhoAlunoFeedbackRequestDTO requestDTO =
//                new ConselhoAlunoFeedbackRequestDTO(
//                        alunoCriado.getId(),
//                        conselhoCriado.getId(),
//                        pedagogicoCriado.getId(),
//                        "Agilidade",
//                        "Comunicação",
//                        "Falar com menos medo"
//                );
//
//        ConselhoAlunoFeedbackResponseDTO criado =
//                conselhoAlunoFeedbackService.create(requestDTO);
//
//        ConselhoAlunoFeedbackRequestDTO atualizadoRequest =
//                new ConselhoAlunoFeedbackRequestDTO(
//                        alunoCriado.getId(),
//                        conselhoCriado.getId(),
//                        pedagogicoCriado.getId(),
//                        "Flexível",
//                        "Falta demais",
//                        "Melhorar presença"
//                );
//
//        ConselhoAlunoFeedbackResponseDTO atualizado =
//                conselhoAlunoFeedbackService.update(criado.id(), atualizadoRequest);
//
//        AssertionsForClassTypes.assertThat(atualizado).isNotNull();
//        AssertionsForClassTypes.assertThat(atualizado.id()).isEqualTo(criado.id());
//    }
//
//    @Test
//    void deveDeletarConselhoAlunoFeedbackComSucesso() {
//
//        ConselhoAlunoFeedbackRequestDTO requestDTO =
//                new ConselhoAlunoFeedbackRequestDTO(
//                        alunoCriado.getId(),
//                        conselhoCriado.getId(),
//                        pedagogicoCriado.getId(),
//                        "Comunicativo",
//                        "Menas conversa",
//                        "Focar mais em front-end"
//                );
//
//        ConselhoAlunoFeedbackResponseDTO responseDTO = conselhoAlunoFeedbackService.create(requestDTO);
//
//        // Delete por ID correto
//        conselhoAlunoFeedbackService.delete(responseDTO.id());
//
//        // Validar remoção
//        assertThat(
//                conselhoAlunoFeedbackRepository.findById(responseDTO.id())
//        ).isEmpty();
//    }
//}