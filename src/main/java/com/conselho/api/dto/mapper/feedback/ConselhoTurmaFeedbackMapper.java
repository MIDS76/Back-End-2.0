package com.conselho.api.dto.mapper.feedback;

import com.conselho.api.dto.request.feedback.ConselhoTurmaFeedbackRequestDTO;
import com.conselho.api.dto.response.feedback.ConselhoTurmaFeedbackResponseDTO;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.pedagogico.PedagogicoNaoExiste;
import com.conselho.api.model.feedback.ConselhoTurmaFeedback;
import com.conselho.api.model.entity.Pedagogico;
import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.repository.entity.AlunoRepository;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.entity.PedagogicoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConselhoTurmaFeedbackMapper {
    private AlunoRepository alunoRepository;
    private PedagogicoRepository pedagogicoRepository;
    private ConselhoRepository conselhoRepository;

    public ConselhoTurmaFeedback paraEntidade(ConselhoTurmaFeedbackRequestDTO request){
        ConselhoTurmaFeedback turmaFeedback = new ConselhoTurmaFeedback();

        Conselho conselho = new Conselho();
        conselho.setId(request.idConselho());

        Pedagogico pedagogico = new Pedagogico();
        pedagogico.setId(request.idPedagogico());

        turmaFeedback.setConselho(conselho);
        turmaFeedback.setPedagogico(pedagogico);

        turmaFeedback.setPontosMelhoria(request.pontosMelhoria());
        turmaFeedback.setPontosPositivos(request.pontosPositivos());
        turmaFeedback.setSugestao(request.sugestao());

        return turmaFeedback;
    }

    public ConselhoTurmaFeedbackResponseDTO paraResposta(ConselhoTurmaFeedback conselhoTurmaFeedback){
        return new ConselhoTurmaFeedbackResponseDTO(
                conselhoTurmaFeedback.getId(),
                conselhoTurmaFeedback.getConselho().getId(),
                conselhoTurmaFeedback.getPedagogico().getId(),
                conselhoTurmaFeedback.getPedagogico().getNome(),
                conselhoTurmaFeedback.getPontosPositivos(),
                conselhoTurmaFeedback.getPontosMelhoria(),
                conselhoTurmaFeedback.getSugestao()
        );
    }

    public ConselhoTurmaFeedback verificarUpdate(ConselhoTurmaFeedbackRequestDTO request, ConselhoTurmaFeedback conselhoTurmaFeedback){
        if (request.pontosPositivos() != null && !request.pontosPositivos().equals(conselhoTurmaFeedback.getPontosPositivos())){
            conselhoTurmaFeedback.setPontosPositivos(request.pontosPositivos());
        }

        if (request.pontosMelhoria() != null && !request.pontosMelhoria().equals(conselhoTurmaFeedback.getPontosMelhoria())){
            conselhoTurmaFeedback.setPontosMelhoria(request.pontosMelhoria());
        }

        if (request.sugestao() != null && !request.sugestao().equals(conselhoTurmaFeedback.getSugestao())){
            conselhoTurmaFeedback.setSugestao(request.sugestao());
        }

        if (request.idConselho() != null && (conselhoTurmaFeedback.getConselho() == null || !request.idConselho().equals(conselhoTurmaFeedback.getConselho().getId()))){
            Conselho conselho = conselhoRepository.findById(request.idConselho())
                    .orElseThrow(ConselhoNaoExiste::new);

            conselhoTurmaFeedback.setConselho(conselho);
        }

        if (request.idPedagogico() != null && (conselhoTurmaFeedback.getPedagogico() == null || !request.idPedagogico().equals(conselhoTurmaFeedback.getPedagogico().getId()))){
            Pedagogico pedagogico = pedagogicoRepository.findById(request.idPedagogico())
                    .orElseThrow(PedagogicoNaoExiste::new);

            conselhoTurmaFeedback.setPedagogico(pedagogico);
        }

        return conselhoTurmaFeedback;
    }
}
