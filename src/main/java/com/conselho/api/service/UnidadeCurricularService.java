package com.conselho.api.service;

import com.conselho.api.dto.mapper.UnidadeCurricularMapper;
import com.conselho.api.dto.request.UnidadeCurricularRequest;
import com.conselho.api.dto.response.UnidadeCurricularResponse;
import com.conselho.api.exception.unidadeCurricular.UnidadeCurricularExisteException;
import com.conselho.api.exception.unidadeCurricular.UnidadeCurricularNaoExisteException;
import com.conselho.api.model.unidadeCurricular.UnidadeCurricular;
import com.conselho.api.repository.UnidadeCurricularRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class UnidadeCurricularService {

    private UnidadeCurricularMapper mapper;
    private UnidadeCurricularRepository repository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UnidadeCurricularResponse criarUnidadeCurricular(UnidadeCurricularRequest unidadeCurricularRequest){
        if(repository.existsByNome(unidadeCurricularRequest.nome())){
            throw new UnidadeCurricularExisteException();
        }
        return mapper.paraResposta(repository.save(mapper.paraEntidade(unidadeCurricularRequest)));
    }

    public List<UnidadeCurricularResponse> buscarTodasUnidades () {
        return repository.findAll()
                .stream()
                .map(mapper::paraResposta)
                .toList();
    }

    public UnidadeCurricularResponse buscarUnidadesPorId(Long id){
        UnidadeCurricular unidadeCurricular = repository.findById(id).orElseThrow(()->
                new UnidadeCurricularNaoExisteException());
        return mapper.paraResposta(unidadeCurricular);
    }

    public UnidadeCurricularResponse atualizarUnidadeCurricular(Long id, UnidadeCurricularRequest unidadeCurricularRequest){
        UnidadeCurricular unidadeCurricular = repository.findById(id).orElseThrow(() ->
                new UnidadeCurricularNaoExisteException());

        UnidadeCurricular newUnidadeCurricular = mapper.paraUpdate(unidadeCurricularRequest, unidadeCurricular);
        repository.save(newUnidadeCurricular);
        return mapper.paraResposta(newUnidadeCurricular);
    }

    public UnidadeCurricularResponse deletarUnidadeCurricular(Long id) {
        UnidadeCurricular unidadeCurricular = repository.findById(id).orElseThrow(() ->
                new UnidadeCurricularNaoExisteException());

        repository.deleteById(id);
        return mapper.paraResposta(unidadeCurricular);
    }

    @Transactional
    public void processarJson(MultipartFile file) throws IOException {

        List<UnidadeCurricularRequest> unidadesCurricularesRequestDTO = objectMapper.readValue(file.getInputStream(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, UnidadeCurricularRequest.class));


        for (UnidadeCurricularRequest unidadeCurricularRequest : unidadesCurricularesRequestDTO) {
            if (unidadeCurricularRequest.nome() != null && !unidadeCurricularRequest.nome().isEmpty()) {

                UnidadeCurricular unidadeCurricular = mapper.paraEntidade(unidadeCurricularRequest);
                repository.save(unidadeCurricular);
            }
        }
    }
}
