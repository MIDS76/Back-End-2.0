package com.conselho.api.service.preConselho;

import com.conselho.api.dto.mapper.preConselho.PreConselhoMapper;
import com.conselho.api.dto.mapper.preConselho.PreConselhoProfessorMapper;
import com.conselho.api.dto.request.preConselho.PreConselhoRequestDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoFeedbacksResponseDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoProfessorResponseDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoResponseDTO;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.preConselho.PreConselhoExisteException;
import com.conselho.api.exception.preConselho.PreConselhoNaoExisteException;
import com.conselho.api.model.preConselho.PreConselho;
import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.notificacao.event.PreConselhoCriadoEvent;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.PreConselhoProfessorRepository;
import com.conselho.api.repository.preConselho.PreConselhoRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PreConselhoService {

    private final PreConselhoMapper preConselhoMapper;
    private final PreConselhoRepository preConselhoRepository;
    private final ConselhoRepository conselhoRepository;
    private final ApplicationEventPublisher publisher;

    // CREATE
    @Transactional
    public PreConselhoResponseDTO criarPreConselhoAutomatico (PreConselhoRequestDTO request){

        // VERIFICA SE EXISTE O CONSELHO
        Conselho conselho = conselhoRepository.findById(request.idConselho())
                .orElseThrow(ConselhoNaoExiste::new);

        // VERIFICA SE EXISTE ESSE PRE CONSELHO COM ID DO CONSELHO (EVITA DUPLICAÇÃO)
        if (preConselhoRepository.existsByConselhoId(request.idConselho())){
            throw new PreConselhoExisteException();
        }

        PreConselho preConselho = preConselhoMapper.paraEntidade(request);
        preConselho.setConselho(conselho);

        // AQUI VOU CRIAR O PRE CONSELHO COM INFORMAÇÕES VALIDADAS
        PreConselho preConselhoSalvo = preConselhoRepository.save(preConselho);

        Long representante1 = preConselhoSalvo.getConselho().getRepresentante1().getId();
        Long representante2 = preConselhoSalvo.getConselho().getRepresentante2().getId();

        publisher.publishEvent(new PreConselhoCriadoEvent(
                preConselhoSalvo.getId(),
                conselho.getId(),
                representante1,
                representante2
        ));

        return preConselhoMapper.paraResposta(preConselhoSalvo);
    }

    // BUSCAR TODOS
    public List<PreConselhoResponseDTO> buscarTodos(){
        return preConselhoRepository.findAll()
                .stream()
                .map(preConselhoMapper::paraResposta)
                .toList();
    }

    // BUSCAR POR ID
    public PreConselhoResponseDTO buscarPorId(Long id){
        PreConselho preConselho = preConselhoRepository.findById(id)
                .orElseThrow(PreConselhoNaoExisteException::new);

        return preConselhoMapper.paraResposta(preConselho);
    }

    // UPDATE
    public PreConselhoResponseDTO update (Long id, PreConselhoRequestDTO request){
        PreConselho preConselho = preConselhoRepository.findById(id)
                .orElseThrow(PreConselhoNaoExisteException::new);

        PreConselho preConselhoAtualizado = preConselhoMapper.verificarUpdate(request, preConselho);

        return preConselhoMapper.paraResposta(preConselhoAtualizado);
    }

    // DELETE
    public void delete (Long id){
        if (!preConselhoRepository.existsById(id)){
            throw new PreConselhoNaoExisteException();
        }

    }

    // BUSCAR TODOS FEEDBACKS DO PRE CONSELHO POR ID PRE CONSELHO
    public PreConselhoFeedbacksResponseDTO buscarTodosFeedbacks(Long id) {
        PreConselho preConselho = preConselhoRepository.findById(id)
                .orElseThrow(PreConselhoNaoExisteException::new);

        return preConselhoMapper.paraRespostaFeedback(preConselho);
    }
}
