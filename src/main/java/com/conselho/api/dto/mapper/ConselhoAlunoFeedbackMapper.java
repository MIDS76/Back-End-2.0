package com.conselho.api.dto.mapper;

import com.conselho.api.dto.request.ConselhoAlunoFeedbackRequestDTO;
import com.conselho.api.dto.response.ConselhoAlunoFeedbackResponseDTO;
import com.conselho.api.exception.aluno.AlunoNaoExisteException;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.pedagogico.PedagogicoNaoExiste;
import com.conselho.api.model.Aluno;
import com.conselho.api.model.feedback.ConselhoAlunoFeedback;
import com.conselho.api.model.Pedagogico;
import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.repository.AlunoRepository;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.PedagogicoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConselhoAlunoFeedbackMapper {
    private AlunoRepository alunoRepository;
    private PedagogicoRepository pedagogicoRepository;
    private ConselhoRepository conselhoRepository;

    public ConselhoAlunoFeedback paraEntidade (ConselhoAlunoFeedbackRequestDTO request){
        ConselhoAlunoFeedback conselhoAlunoFeedback = new ConselhoAlunoFeedback();

        Conselho conselho = new Conselho();
        conselho.setId(request.idConselho());

        Pedagogico pedagogico = new Pedagogico();
        pedagogico.setId(request.idPedagogico());

        Aluno aluno = new Aluno();
        aluno.setId(request.idAluno());

        conselhoAlunoFeedback.setConselho(conselho);
        conselhoAlunoFeedback.setPedagogico(pedagogico);
        conselhoAlunoFeedback.setAluno(aluno);

        conselhoAlunoFeedback.setPontosMelhoria(request.pontosMelhoria());
        conselhoAlunoFeedback.setPontosPositivos(request.pontosPositivos());
        conselhoAlunoFeedback.setSugestao(request.sugestao());

        return conselhoAlunoFeedback;
    }

    public ConselhoAlunoFeedbackResponseDTO paraResposta(ConselhoAlunoFeedback conselhoAlunoFeedback){
        return new ConselhoAlunoFeedbackResponseDTO(
                conselhoAlunoFeedback.getId(),
                conselhoAlunoFeedback.getConselho().getId(),
                conselhoAlunoFeedback.getPedagogico().getId(),
                conselhoAlunoFeedback.getPedagogico().getNome(),
                conselhoAlunoFeedback.getAluno().getId(),
                conselhoAlunoFeedback.getAluno().getNome(),
                conselhoAlunoFeedback.getPontosPositivos(),
                conselhoAlunoFeedback.getPontosMelhoria(),
                conselhoAlunoFeedback.getSugestao()
        );
    }

    public ConselhoAlunoFeedback verificarUpdate(ConselhoAlunoFeedbackRequestDTO request, ConselhoAlunoFeedback conselhoAlunoFeedback){
        if (request.pontosPositivos() != null && !request.pontosPositivos().equals(conselhoAlunoFeedback.getPontosPositivos())){
            conselhoAlunoFeedback.setPontosPositivos(request.pontosPositivos());
        }

        if (request.pontosMelhoria() != null && !request.pontosMelhoria().equals(conselhoAlunoFeedback.getPontosMelhoria())){
            conselhoAlunoFeedback.setPontosMelhoria(request.pontosMelhoria());
        }

        if (request.sugestao() != null && !request.sugestao().equals(conselhoAlunoFeedback.getSugestao())){
            conselhoAlunoFeedback.setSugestao(request.sugestao());
        }

        if (request.idAluno() != null && (conselhoAlunoFeedback.getAluno() == null || !request.idAluno().equals(conselhoAlunoFeedback.getAluno().getId()))){
            Aluno aluno = alunoRepository.findById(request.idAluno())
                    .orElseThrow(AlunoNaoExisteException::new);

            conselhoAlunoFeedback.setAluno(aluno);
        }

        if (request.idConselho() != null && (conselhoAlunoFeedback.getConselho() == null || !request.idConselho().equals(conselhoAlunoFeedback.getConselho().getId()))){
            Conselho conselho = conselhoRepository.findById(request.idConselho())
                    .orElseThrow(ConselhoNaoExiste::new);

            conselhoAlunoFeedback.setConselho(conselho);
        }

        if (request.idPedagogico() != null && (conselhoAlunoFeedback.getPedagogico() == null || !request.idPedagogico().equals(conselhoAlunoFeedback.getPedagogico().getId()))){
            Pedagogico pedagogico = pedagogicoRepository.findById(request.idPedagogico())
                    .orElseThrow(PedagogicoNaoExiste::new);

            conselhoAlunoFeedback.setPedagogico(pedagogico);
        }

        return conselhoAlunoFeedback;
    }
}
