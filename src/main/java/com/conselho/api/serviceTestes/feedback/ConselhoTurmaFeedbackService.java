package com.conselho.api.serviceTestes.feedback;

import com.conselho.api.dto.mapper.feedback.ConselhoTurmaFeedbackMapper;
import com.conselho.api.dto.request.feedback.ConselhoTurmaFeedbackRequestDTO;
import com.conselho.api.dto.response.feedback.ConselhoTurmaFeedbackResponseDTO;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.conselhoTurmaFeedback.ConselhoTurmaFeedbackExisteException;
import com.conselho.api.exception.conselhoTurmaFeedback.ConselhoTurmaFeedbackNaoExisteException;
import com.conselho.api.exception.pedagogico.PedagogicoNaoExiste;
import com.conselho.api.model.feedback.ConselhoTurmaFeedback;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.entity.PedagogicoRepository;
import com.conselho.api.repository.feedback.ConselhoTurmaFeedbackRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ConselhoTurmaFeedbackService {
    private ConselhoTurmaFeedbackMapper mapper;
    private ConselhoTurmaFeedbackRepository repository;
    private PedagogicoRepository pedagogicoRepository;
    private ConselhoRepository conselhoRepository;

    // CREATE
    public ConselhoTurmaFeedbackResponseDTO create (ConselhoTurmaFeedbackRequestDTO request){
        ConselhoTurmaFeedback conselhoTurmaFeedback = mapper.paraEntidade(request);

        // VERIFICA SE EXISTE O CONSELHO
        conselhoTurmaFeedback.setConselho(conselhoRepository.findById(request.idConselho())
                .orElseThrow(ConselhoNaoExiste::new));

        conselhoTurmaFeedback.setPedagogico(pedagogicoRepository.findById(request.idPedagogico())
                .orElseThrow(PedagogicoNaoExiste::new));

        // VERIFICA SE EXISTE ESSE PRE CONSELHO COM ID DO CONSELHO (EVITA DUPLICAÇÃO)
        if (repository.existsByConselhoId(request.idConselho())){
            throw new ConselhoTurmaFeedbackExisteException();
        }

        ConselhoTurmaFeedback conselhoAlunoFeedbackSalvo = repository.save(conselhoTurmaFeedback);

        return mapper.paraResposta(conselhoAlunoFeedbackSalvo);
    }

    // BUSCAR TODOS
    public List<ConselhoTurmaFeedbackResponseDTO> buscarTodos(){
        return repository.findAll()
                .stream()
                .map(mapper::paraResposta)
                .toList();
    }

    // BUSCAR POR ID
    public ConselhoTurmaFeedbackResponseDTO buscarPorId(Long id){
        ConselhoTurmaFeedback turmaFeedback = repository.findById(id)
                .orElseThrow(ConselhoTurmaFeedbackNaoExisteException::new);

        return mapper.paraResposta(turmaFeedback);
    }

    // ATUALIZAR
    public ConselhoTurmaFeedbackResponseDTO update (Long id, ConselhoTurmaFeedbackRequestDTO request){
        ConselhoTurmaFeedback turmaFeedback = repository.findById(id)
                .orElseThrow(ConselhoTurmaFeedbackNaoExisteException::new);

        ConselhoTurmaFeedback atualizado = mapper.verificarUpdate(request, turmaFeedback);

        return mapper.paraResposta(repository.save(atualizado));
    }

    // DELETAR
    public void delete (Long id){
        if (!repository.existsById(id)){
            throw new ConselhoTurmaFeedbackNaoExisteException();
        }

        repository.deleteById(id);
    }
}