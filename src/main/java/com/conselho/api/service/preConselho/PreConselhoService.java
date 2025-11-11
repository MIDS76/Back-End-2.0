package com.conselho.api.service.preConselho;

import com.conselho.api.dto.mapper.preConselho.PreConselhoMapper;
import com.conselho.api.dto.request.preConselho.PreConselhoRequestDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoResponseDTO;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.preConselho.PreConselhoExisteException;
import com.conselho.api.exception.preConselho.PreConselhoNaoExisteException;
import com.conselho.api.model.preConselho.PreConselho;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.preConselho.PreConselhoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PreConselhoService {

    private PreConselhoMapper preConselhoMapper;
    private PreConselhoRepository preConselhoRepository;
    private ConselhoRepository conselhoRepository;

    // CREATE
    @Transactional
    public PreConselhoResponseDTO criarPreConselhoAutomatico (PreConselhoRequestDTO request){
        PreConselho preConselho = preConselhoMapper.paraEntidade(request);

        // VERIFICA SE EXISTE O CONSELHO
        preConselho.setConselho(conselhoRepository.findById(request.idConselho())
                .orElseThrow(ConselhoNaoExiste::new));

        // VERIFICA SE EXISTE ESSE PRE CONSELHO COM ID DO CONSELHO (EVITA DUPLICAÇÃO)
        if (preConselhoRepository.existsByConselhoId(request.idConselho())){
            throw new PreConselhoExisteException();
        }

        // AQUI VOU CRIAR O PRE CONSELHO COM INFORMAÇÕES VALIDADAS
        PreConselho preConselhoSalvo = preConselhoRepository.save(preConselho);

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

        return preConselhoMapper.paraResposta(preConselhoRepository.save(preConselhoAtualizado));
    }

    // DELETE
    public void delete (Long id){
        if (!preConselhoRepository.existsById(id)){
            throw new PreConselhoNaoExisteException();
        }
        preConselhoRepository.deleteById(id);
    }
}
