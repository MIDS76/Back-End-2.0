package com.conselho.api.testeIntegracao.feedback;

import com.conselho.api.dto.request.feedback.ConselhoAlunoFeedbackRequestDTO;
import com.conselho.api.dto.response.feedback.ConselhoAlunoFeedbackResponseDTO;
import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.model.entity.Aluno;
import com.conselho.api.model.entity.Pedagogico;
import com.conselho.api.model.feedback.ConselhoAlunoFeedback;
import com.conselho.api.repository.feedback.ConselhoAlunoFeedbackRepository;
import com.conselho.api.service.feedback.ConselhoAlunoFeedbackService;
import jakarta.transaction.Transactional;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ConselhoAlunoFeedbackTesteIntegracao {

    private final ConselhoAlunoFeedbackService conselhoAlunoFeedbackService;
    private final ConselhoAlunoFeedbackRepository conselhoAlunoFeedbackRepository;

    public ConselhoAlunoFeedbackTesteIntegracao (
        ConselhoAlunoFeedbackService conselhoAlunoFeedbackService,
        ConselhoAlunoFeedbackRepository conselhoAlunoFeedbackRepository
    ){
        this.conselhoAlunoFeedbackService = conselhoAlunoFeedbackService;
        this.conselhoAlunoFeedbackRepository = conselhoAlunoFeedbackRepository;
    }

    @BeforeEach
    void setup() {
        ConselhoAlunoFeedback conselhoAlunoFeedback = new ConselhoAlunoFeedback();
        conselhoAlunoFeedback.setAluno(new Aluno());
        conselhoAlunoFeedback.setConselho(new Conselho());
        conselhoAlunoFeedback.setPedagogico(new Pedagogico());
        conselhoAlunoFeedback.setPontosPositivos("Comunicativo");
        conselhoAlunoFeedback.setPontosMelhoria("Menas conversa");
        conselhoAlunoFeedback.setSugestao("Focar mais em front-end");

        conselhoAlunoFeedbackRepository.save(conselhoAlunoFeedback);
    }

//    @Test
//    void deveListarConselhoAlunoFeedback() {
//        ConselhoAlunoFeedbackRequestDTO requestDTO = new ConselhoAlunoFeedbackRequestDTO(3L,2L,1L,"Comunicatico", "Menas conversa", "focar mais em front end");
//        ConselhoAlunoFeedbackResponseDTO responseDTO = conselhoAlunoFeedbackService.create(requestDTO);
//        ConselhoAlunoFeedbackResponseDTO resposta = conselhoAlunoFeedbackService.buscarPorId(responseDTO.id());
//
//        List<ConselhoAlunoFeedbackResponseDTO> lista = conselhoAlunoFeedbackService.buscarTodos();
//
//        assertThat(lista).isNotEmpty();
//    }

    @Test
    void deveAtualizarUnidadeCurricularComSucesso() {
        ConselhoAlunoFeedbackRequestDTO requestDTO = new ConselhoAlunoFeedbackRequestDTO(2L, 1L, 2L, "Agilidade", "Comunicação", "Falar com menos medo");
        ConselhoAlunoFeedbackResponseDTO responseDTO = conselhoAlunoFeedbackService.create(requestDTO);
        ConselhoAlunoFeedbackRequestDTO atualizadoRequest =  new ConselhoAlunoFeedbackRequestDTO(2L, 4L,5L, "Flexivel", "Falta demais", "Melhorar na presença");
        ConselhoAlunoFeedbackResponseDTO atualizado = conselhoAlunoFeedbackService.update(responseDTO.id(), atualizadoRequest);

        AssertionsForClassTypes.assertThat(atualizado).isNotNull();
        AssertionsForClassTypes.assertThat(atualizado.id()).isEqualTo(2);
    }

//    @Test
//    void deveDeletarConselhoAlunoFeedbackComSucesso() {
//        ConselhoAlunoFeedbackRequestDTO requestDTO = new ConselhoAlunoFeedbackRequestDTO(2L,1L,3L,"Comunicativo", "Menas conversa", "Focar mais em front-end");
//        ConselhoAlunoFeedbackResponseDTO responseDTO = conselhoAlunoFeedbackService.delete(requestDTO);
//        conselhoAlunoFeedbackService.delete(responseDTO.id());
//
//        AssertionsForClassTypes.assertThat(conselhoAlunoFeedbackRepository.findById(responseDTO.id())).isEmpty();
//    }
}
