package com.conselho.api.dto.mapper;

import com.conselho.api.dto.mapper.feedback.ConselhoAlunoFeedbackMapper;
import com.conselho.api.dto.mapper.feedback.ConselhoTurmaFeedbackMapper;
import com.conselho.api.dto.request.ConselhoRequestDTO;
import com.conselho.api.dto.response.ConselhoFeedbacksResponseDTO;
import com.conselho.api.dto.response.ConselhoResponseDTO;
import com.conselho.api.exception.conselhoTurmaFeedback.ConselhoTurmaFeedbackNaoExisteException;
import com.conselho.api.exception.pedagogico.PedagogicoNaoExiste;
import com.conselho.api.exception.representante.RepresentanteNaoExiste;
import com.conselho.api.exception.turma.TurmaNaoExisteException;
import com.conselho.api.model.entity.Aluno;
import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.model.entity.Pedagogico;
import com.conselho.api.model.Turma;
import com.conselho.api.repository.entity.AlunoRepository;
import com.conselho.api.repository.entity.PedagogicoRepository;
import com.conselho.api.repository.TurmaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConselhoMapper {

    private PedagogicoRepository pedagogicoRepository;
    private AlunoRepository alunoRepository;
    private TurmaRepository turmaRepository;
    private ConselhoAlunoFeedbackMapper alunoFeedbackMapper;
    private ConselhoTurmaFeedbackMapper turmaFeedbackMapper;

    public Conselho paraEntidade(ConselhoRequestDTO request) {
        Conselho conselho = new Conselho();

        Turma turma = new Turma();
        turma.setId(request.idTurma());

        Aluno representante1 = new Aluno();
        representante1.setId(request.idRepresentante1());

        Aluno representante2 = new Aluno();
        representante2.setId(request.idRepresentante2());

        Pedagogico pedagogico = new Pedagogico();
        pedagogico.setId(request.idPedagogico());

        conselho.setTurma(turma);
        conselho.setRepresentante1(representante1);
        conselho.setRepresentante2(representante2);
        conselho.setPedagogico(pedagogico);
        conselho.setDataInicio(request.dataInicio());
        conselho.setDataFim(request.dataFim());

        return conselho;
    }

    public ConselhoResponseDTO paraResposta(Conselho conselho) {
        return new ConselhoResponseDTO(
                conselho.getId(),
                conselho.getTurma().getId(),
                conselho.getTurma().getNome(),
                conselho.getRepresentante1().getId(),
                conselho.getRepresentante1().getNome(),
                conselho.getRepresentante2().getId(),
                conselho.getRepresentante2().getNome(),
                conselho.getPedagogico().getId(),
                conselho.getPedagogico().getNome(),
                conselho.getDataInicio(),
                conselho.getDataFim(),
                conselho.getEtapas().name()
        );
    }

    public ConselhoFeedbacksResponseDTO paraRespostaFeedbacks(Conselho conselho) {
        return new ConselhoFeedbacksResponseDTO(
                conselho.getConselhoAlunoFeedback()
                        .stream()
                        .map(alunoFeedbackMapper::paraResposta)
                        .toList(),

                conselho.getConselhoTurmaFeedbacks()
                        .stream()
                        .findFirst()
                        .map(turmaFeedbackMapper::paraResposta)
                        .orElse(null)
        );
    }

    public Conselho verificarUpdate(ConselhoRequestDTO request, Conselho conselho){
        if (request.dataFim() != null && !request.dataFim().equals(conselho.getDataFim())){
            conselho.setDataFim(request.dataFim());
        }

        if (request.dataInicio() != null && !request.dataInicio().equals(conselho.getDataInicio())){
            conselho.setDataInicio(request.dataInicio());
        }


        if (request.idPedagogico() != null && (conselho.getPedagogico() == null || !request.idPedagogico().equals(conselho.getPedagogico().getId()))){
            Pedagogico novoPedagogico = pedagogicoRepository.findById(request.idPedagogico())
                    .orElseThrow(PedagogicoNaoExiste::new);

            conselho.setPedagogico(novoPedagogico);
        }

        if (request.idRepresentante1() != null && (conselho.getRepresentante1() == null || !request.idRepresentante1().equals(conselho.getRepresentante1().getId()))){
            Aluno aluno1 = alunoRepository.findById(request.idRepresentante1())
                    .orElseThrow(RepresentanteNaoExiste::new);

            conselho.setRepresentante1(aluno1);
        }

        if (request.idRepresentante2() != null && (conselho.getRepresentante2() == null || !request.idRepresentante2().equals(conselho.getRepresentante2().getId()))){
            Aluno aluno2 = alunoRepository.findById(request.idRepresentante2())
                    .orElseThrow(RepresentanteNaoExiste::new);

            conselho.setRepresentante2(aluno2);
        }

        if (request.idTurma() != null && (conselho.getTurma() == null || !request.idTurma().equals(conselho.getTurma().getId()))){
            Turma turma = turmaRepository.findById(request.idTurma())
                    .orElseThrow(TurmaNaoExisteException::new);

            conselho.setTurma(turma);
        }

        return conselho;
    }
}
