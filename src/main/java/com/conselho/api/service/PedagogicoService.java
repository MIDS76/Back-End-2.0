package com.conselho.api.service;

import com.conselho.api.dto.mapper.PedagogicoMapper;
import com.conselho.api.dto.request.PedagogicoRequest;
import com.conselho.api.dto.response.PedagogicoResponse;
import com.conselho.api.exception.pedagogico.PedagogicoJaExiste;
import com.conselho.api.exception.pedagogico.PedagogicoNaoExiste;
import com.conselho.api.model.Pedagogico;
import com.conselho.api.repository.PedagogicoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PedagogicoService {
    private final PedagogicoMapper mapper;
    private final PedagogicoRepository repository;

    // CREATE
    public PedagogicoResponse criarPedagogico (PedagogicoRequest request){
        if (repository.existsByNome(request.nome())){
            throw new PedagogicoJaExiste();
        }
        return mapper.paraResposta(repository.save(mapper.paraEntidade(request)));
    }

    // DELETE
    public void deletarPedagogico (Long id){
        if (!repository.existsById(id)){
            throw new PedagogicoNaoExiste();
        }
        repository.deleteById(id);
    }

    // BUSCAR TODOS
    public List<PedagogicoResponse> buscarTodos (){
        return repository.findAll()
                .stream()
                .map(mapper::paraResposta)
                .toList();
    }

    // BUSCAR POR ID
    public PedagogicoResponse buscarPorId(Long id){
        Pedagogico pedagogico = repository.findById(id)
                .orElseThrow(PedagogicoNaoExiste::new);

        return mapper.paraResposta(pedagogico);
    }

    // UPDATE
    public PedagogicoResponse update (Long id, PedagogicoRequest request){
        Pedagogico pedagogico = repository.findById(id)
                .orElseThrow(PedagogicoNaoExiste::new);

        // fazer mapper de verificar update // esperando codigo do usuario
        Pedagogico pedagogicoAtualizado = mapper.verificarUpdate(request, pedagogico);

        return mapper.paraResposta(repository.save(pedagogicoAtualizado));
    }
}