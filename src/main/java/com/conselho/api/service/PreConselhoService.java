package com.conselho.api.service;

import com.conselho.api.dto.mapper.PreConselhoMapper;
import com.conselho.api.dto.request.PreConselhoRequest;
import com.conselho.api.dto.response.PreConselhoResponse;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.conselho.DataForaDoPeriodoConselhoException;
import com.conselho.api.exception.preConselho.PreConselhoExisteException;
import com.conselho.api.exception.preConselho.PreConselhoNaoExisteException;
import com.conselho.api.model.preConselho.PreConselho;
import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.PreConselhoRepository;
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
    public PreConselhoResponse criarPreConselhoAutomatico (PreConselhoRequest request){

        // VERIFICA SE EXISTE O CONSELHO
        Conselho conselho = conselhoRepository.findById(request.idConselho())
                .orElseThrow(ConselhoNaoExiste::new);

        // VERIFICA SE EXISTE ESSE PRE CONSELHO COM ID DO CONSELHO (EVITA DUPLICAÇÃO)
        if (preConselhoRepository.existsByConselhoId(request.idConselho())){
            throw new PreConselhoExisteException();
        }

        // VERIFICA SE DATAS DO PRE CONSELHO ESTÁ FORA DO PERIODO DO CONSELHO
        if (request.dataInicio().isBefore(conselho.getDataInicio()) ||
                request.dataFim().isAfter(conselho.getDataFim())) {
            throw new DataForaDoPeriodoConselhoException();
        }

        PreConselho preConselho = preConselhoMapper.paraEntidade(request);
        preConselho.setConselho(conselho);

        // AQUI VOU CRIAR O PRE CONSELHO COM INFORMAÇÕES VALIDADAS
        PreConselho preConselhoSalvo = preConselhoRepository.save(preConselho);

        return preConselhoMapper.paraResposta(preConselhoSalvo);
    }

    // BUSCAR TODOS
    public List<PreConselhoResponse> buscarTodos(){
        return preConselhoRepository.findAll()
                .stream()
                .map(preConselhoMapper::paraResposta)
                .toList();
    }

    // BUSCAR POR ID
    public PreConselhoResponse buscarPorId(Long id){
        PreConselho preConselho = preConselhoRepository.findById(id)
                .orElseThrow(PreConselhoNaoExisteException::new);

        return preConselhoMapper.paraResposta(preConselho);
    }

    // UPDATE
    public PreConselhoResponse update (Long id, PreConselhoRequest request){
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
}
