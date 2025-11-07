package com.conselho.api.service;

import com.conselho.api.dto.mapper.ConselhoAlunoFeedbackMapper;
import com.conselho.api.dto.request.ConselhoAlunoFeedbackRequestDTO;
import com.conselho.api.dto.response.ConselhoAlunoFeedbackResponseDTO;
import com.conselho.api.exception.aluno.AlunoNaoExisteException;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.conselhoAlunoFeedback.ConselhoAlunoFeedbackExisteException;
import com.conselho.api.exception.conselhoAlunoFeedback.ConselhoAlunoFeedbackNaoExisteException;
import com.conselho.api.exception.pedagogico.PedagogicoNaoExiste;
import com.conselho.api.model.Aluno;
import com.conselho.api.model.Pedagogico;
import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.model.feedback.ConselhoAlunoFeedback;
import com.conselho.api.repository.AlunoRepository;
import com.conselho.api.repository.ConselhoAlunoFeedbackRepository;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.PedagogicoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ConselhoAlunoFeedbackService {
    private ConselhoAlunoFeedbackMapper mapper;
    private ConselhoAlunoFeedbackRepository repository;
    private PedagogicoRepository pedagogicoRepository;
    private ConselhoRepository conselhoRepository;
    private AlunoRepository alunoRepository;

    // CREATE
    public ConselhoAlunoFeedbackResponseDTO create (ConselhoAlunoFeedbackRequestDTO request){

        Conselho conselho = conselhoRepository.findById(request.idConselho())
                .orElseThrow(ConselhoNaoExiste::new);

        Aluno aluno = alunoRepository.findById(request.idAluno())
                .orElseThrow(AlunoNaoExisteException::new);

        Pedagogico pedagogico = pedagogicoRepository.findById(request.idPedagogico())
                .orElseThrow(PedagogicoNaoExiste::new);

        if (repository.existsByConselhoId(request.idConselho())){
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
    public List<ConselhoAlunoFeedbackResponseDTO> buscarTodos(){
        return repository.findAll()
                .stream()
                .map(mapper::paraResposta)
                .toList();
    }

    // BUSCAR POR ID
    public ConselhoAlunoFeedbackResponseDTO buscarPorId(Long id, ConselhoAlunoFeedbackRequestDTO request){
        ConselhoAlunoFeedback alunoFeedback = repository.findById(id)
                .orElseThrow(ConselhoAlunoFeedbackNaoExisteException::new);

        return mapper.paraResposta(alunoFeedback);
    }

    // ATUALIZAR
    public ConselhoAlunoFeedbackResponseDTO update (Long id, ConselhoAlunoFeedbackRequestDTO request){
        ConselhoAlunoFeedback alunoFeedback = repository.findById(id)
                .orElseThrow(ConselhoAlunoFeedbackNaoExisteException::new);

        ConselhoAlunoFeedback atualizado = mapper.verificarUpdate(request, alunoFeedback);

        return mapper.paraResposta(repository.save(atualizado));
    }

    // DELETAR
    public void delete (Long id){
        if (!repository.existsById(id)){
            throw new ConselhoAlunoFeedbackNaoExisteException();
        }

        repository.deleteById(id);
    }
}