//package com.conselho.api.testeIntegracao.feedback;
//
//import com.conselho.api.dto.request.feedback.ConselhoTurmaFeedbackRequestDTO;
//import com.conselho.api.dto.response.feedback.ConselhoTurmaFeedbackResponseDTO;
//import com.conselho.api.model.conselho.Conselho;
//import com.conselho.api.model.entity.Pedagogico;
//import com.conselho.api.model.feedback.ConselhoTurmaFeedback;
//import com.conselho.api.repository.feedback.ConselhoTurmaFeedbackRepository;
//import com.conselho.api.service.feedback.ConselhoTurmaFeedbackService;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestConstructor;
//
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@SpringBootTest
//@Transactional
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
//public class ConselhoTurmaFeedbackTesteIntegracao {
//
//    private final ConselhoTurmaFeedbackService conselhoTurmaFeedbackService;
//    private final ConselhoTurmaFeedbackRepository conselhoTurmaFeedbackRepository;
//
//    public ConselhoTurmaFeedbackTesteIntegracao(
//          ConselhoTurmaFeedbackService conselhoTurmaFeedbackService,
//          ConselhoTurmaFeedbackRepository conselhoTurmaFeedbackRepository
//    ){
//        this.conselhoTurmaFeedbackService = conselhoTurmaFeedbackService;
//        this.conselhoTurmaFeedbackRepository = conselhoTurmaFeedbackRepository;
//    }
//
//    @BeforeEach
//    void setup() {
//        ConselhoTurmaFeedback conselhoTurmaFeedback = new ConselhoTurmaFeedback();
//        conselhoTurmaFeedback.setId(conselhoTurmaFeedback.getId());
//        conselhoTurmaFeedback.setConselho(new Conselho());
//        conselhoTurmaFeedback.setPedagogico(new Pedagogico());
//        conselhoTurmaFeedback.setPontosPositivos("Comunicativos");
//        conselhoTurmaFeedback.setPontosMelhoria("Menas conversa");
//        conselhoTurmaFeedback.setSugestao("Conversar menos");
//
//        conselhoTurmaFeedbackRepository.save(conselhoTurmaFeedback);
//    }
//
//    @Test
//    void deveListarConselhoTurmaFeedback() {
//        ConselhoTurmaFeedbackRequestDTO requestDTO = new ConselhoTurmaFeedbackRequestDTO(2L,3L,"Comunicativos","Menas conversa paralela", "focar mais");
//        ConselhoTurmaFeedbackResponseDTO responseDTO = conselhoTurmaFeedbackService.create(requestDTO);
//        ConselhoTurmaFeedbackResponseDTO resposta = conselhoTurmaFeedbackService.buscarPorId(responseDTO.id());
//
//        List<ConselhoTurmaFeedbackResponseDTO> listinha = conselhoTurmaFeedbackService.buscarTodos();
//
//    }
//
//    @Test
//    void deveAtualizarConselhoTurmaFeedbackComSucesso() {
//        ConselhoTurmaFeedbackRequestDTO requestDTO = new ConselhoTurmaFeedbackRequestDTO(2L,5L, "Compreensiveis", "Atrasos", "melhorar atrasos");
//        ConselhoTurmaFeedbackResponseDTO responseDTO = conselhoTurmaFeedbackService.create(requestDTO);
//        ConselhoTurmaFeedbackRequestDTO atualizadoRequest = new ConselhoTurmaFeedbackRequestDTO(2L,5L,"Comunicação", "Conversar paralela", "melhorar nas conversas");
//        ConselhoTurmaFeedbackResponseDTO atualizado = conselhoTurmaFeedbackService.update(responseDTO.id(), atualizadoRequest);
//
//        assertThat(atualizado).isNotNull();
//        assertThat(atualizado.id()).isEqualTo(2);
//    }
//
//    @Test
//    void deveDeletarTurmaFeedbackComSucesso(){
//        ConselhoTurmaFeedbackRequestDTO requestDTO = new ConselhoTurmaFeedbackRequestDTO(1L, 2L, "TESTE", "TESTE", "TESTE");
//        ConselhoTurmaFeedbackResponseDTO responseDTO = conselhoTurmaFeedbackService.delete(requestDTO);
//        conselhoTurmaFeedbackService.delete(responseDTO.id());
//
//        assertThat(conselhoTurmaFeedbackRepository.findById(responseDTO.id())).isEmpty();
//
//    }
//}
