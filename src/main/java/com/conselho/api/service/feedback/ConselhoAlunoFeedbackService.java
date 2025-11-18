package com.conselho.api.service.feedback;

import com.conselho.api.dto.mapper.feedback.ConselhoAlunoFeedbackMapper;
import com.conselho.api.dto.request.feedback.ConselhoAlunoFeedbackRequestDTO;
import com.conselho.api.dto.response.feedback.ConselhoAlunoFeedbackResponseDTO;
import com.conselho.api.dto.response.feedback.FeedbackAlunoCompletoResponseDTO;
import com.conselho.api.exception.aluno.AlunoNaoExisteException;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.conselhoAlunoFeedback.ConselhoAlunoFeedbackExisteException;
import com.conselho.api.exception.conselhoAlunoFeedback.ConselhoAlunoFeedbackNaoExisteException;
import com.conselho.api.exception.pedagogico.PedagogicoNaoExiste;
import com.conselho.api.model.AlunoTurma;
import com.conselho.api.model.Turma;
import com.conselho.api.model.entity.Aluno;
import com.conselho.api.model.entity.Pedagogico;
import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.model.feedback.ConselhoAlunoFeedback;
import com.conselho.api.model.feedback.ConselhoTurmaFeedback;
import com.conselho.api.repository.AlunoTurmaRepository;
import com.conselho.api.repository.TurmaRepository;
import com.conselho.api.repository.entity.AlunoRepository;
import com.conselho.api.repository.feedback.ConselhoAlunoFeedbackRepository;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.entity.PedagogicoRepository;
import com.conselho.api.repository.feedback.ConselhoTurmaFeedbackRepository;
import lombok.AllArgsConstructor;
import org.hibernate.sql.model.ast.AbstractTableDelete;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ConselhoAlunoFeedbackService {
    private ConselhoAlunoFeedbackMapper mapper;
    private ConselhoAlunoFeedbackRepository repository;
    private PedagogicoRepository pedagogicoRepository;
    private ConselhoRepository conselhoRepository;
    private AlunoRepository alunoRepository;
    private ConselhoTurmaFeedbackRepository turmaFeedbackRepository;

    // CREATE
    public ConselhoAlunoFeedbackResponseDTO create(ConselhoAlunoFeedbackRequestDTO request) {

        Conselho conselho = conselhoRepository.findById(request.idConselho())
                .orElseThrow(ConselhoNaoExiste::new);

        Aluno aluno = alunoRepository.findById(request.idAluno())
                .orElseThrow(AlunoNaoExisteException::new);

        Pedagogico pedagogico = pedagogicoRepository.findById(request.idPedagogico())
                .orElseThrow(PedagogicoNaoExiste::new);

        if (repository.existsByConselhoId(request.idConselho())) {
            throw new ConselhoAlunoFeedbackExisteException();
        }

        ConselhoAlunoFeedback alunoFeedback = mapper.paraEntidade(request);
        alunoFeedback.setConselho(conselho);
        alunoFeedback.setAluno(aluno);
        alunoFeedback.setPedagogico(pedagogico);

        ConselhoAlunoFeedback conselhoAlunoFeedbackSalvo = repository.save(alunoFeedback);

        return mapper.paraResposta(conselhoAlunoFeedbackSalvo);
    }

    // BUSCAR TODOS
    public List<ConselhoAlunoFeedbackResponseDTO> buscarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::paraResposta)
                .toList();
    }

    // BUSCAR POR ID
    public ConselhoAlunoFeedbackResponseDTO buscarPorId(Long id) {
        ConselhoAlunoFeedback alunoFeedback = repository.findById(id)
                .orElseThrow(ConselhoAlunoFeedbackNaoExisteException::new);

        return mapper.paraResposta(alunoFeedback);
    }

    public FeedbackAlunoCompletoResponseDTO buscarFeedbackAlunoPorConselho(Long idConselho, Long idAluno) {
        Conselho conselho = conselhoRepository.findById(idConselho)
                .orElseThrow(ConselhoNaoExiste::new);

        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(AlunoNaoExisteException::new);

        ConselhoAlunoFeedback alunoFeedback = repository.findByAluno(aluno);
        ConselhoTurmaFeedback turmaFeedback = turmaFeedbackRepository.findByConselho(conselho);

        if(alunoFeedback == null){
            throw new RuntimeException("Feedback ão disponivel para este aluno!");
        }
        if(turmaFeedback == null){
            throw new RuntimeException("Feedback ão disponivel para esta turma!");
        }

        return new FeedbackAlunoCompletoResponseDTO(
                alunoFeedback.getId(),
                alunoFeedback.getConselho().getId(),
                alunoFeedback.getPedagogico().getNome(),
                alunoFeedback.getAluno().getNome(),
                alunoFeedback.getPontosPositivos(),
                alunoFeedback.getPontosMelhoria(),
                alunoFeedback.getSugestao(),
                turmaFeedback.getId(),
                turmaFeedback.getPontosPositivos(),
                turmaFeedback.getPontosMelhoria(),
                turmaFeedback.getSugestao()
        );
    }



    // ATUALIZAR
    public ConselhoAlunoFeedbackResponseDTO update(Long id, ConselhoAlunoFeedbackRequestDTO request) {
        ConselhoAlunoFeedback alunoFeedback = repository.findById(id)
                .orElseThrow(ConselhoAlunoFeedbackNaoExisteException::new);

        ConselhoAlunoFeedback atualizado = mapper.verificarUpdate(request, alunoFeedback);

        return mapper.paraResposta(repository.save(atualizado));
    }

    // DELETAR
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ConselhoAlunoFeedbackNaoExisteException();
        }

        repository.deleteById(id);
    }
}