package com.conselho.api.service;

import com.conselho.api.dto.mapper.UnidadeCurricularMapper;
import com.conselho.api.dto.request.UnidadeCurricularRequestDTO;
import com.conselho.api.dto.response.UnidadeCurricularResponseDTO;
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

    public UnidadeCurricularResponseDTO criarUnidadeCurricular(UnidadeCurricularRequestDTO unidadeCurricularRequestDTO){
        if(repository.existsByNome(unidadeCurricularRequestDTO.nome())){
            throw new UnidadeCurricularExisteException();
        }
        return mapper.paraResposta(repository.save(mapper.paraEntidade(unidadeCurricularRequestDTO)));
    }

    public List<UnidadeCurricularResponseDTO> listarUnidadesCurriculares () {
        return repository.findAll()
                .stream()
                .map(mapper::paraResposta)
                .toList();
    }

    public UnidadeCurricularResponseDTO buscarUnidadesPorId(Long id){
        UnidadeCurricular unidadeCurricular = repository.findById(id).orElseThrow(()->
                new UnidadeCurricularNaoExisteException());
        return mapper.paraResposta(unidadeCurricular);
    }

    public UnidadeCurricularResponseDTO atualizarUnidadeCurricular(Long id, UnidadeCurricularRequestDTO unidadeCurricularRequestDTO){
        UnidadeCurricular unidadeCurricular = repository.findById(id).orElseThrow(() ->
                new UnidadeCurricularNaoExisteException());

        UnidadeCurricular newUnidadeCurricular = mapper.paraUpdate(unidadeCurricularRequestDTO, unidadeCurricular);
        repository.save(newUnidadeCurricular);
        return mapper.paraResposta(newUnidadeCurricular);
    }

    public UnidadeCurricularResponseDTO deletarUnidadeCurricular(Long id) {
        UnidadeCurricular unidadeCurricular = repository.findById(id).orElseThrow(() ->
                new UnidadeCurricularNaoExisteException());

        repository.deleteById(id);
        return mapper.paraResposta(unidadeCurricular);
    }

    @Transactional
    public void processarJson(MultipartFile file) throws IOException {

        List<UnidadeCurricularRequestDTO> unidadesCurricularesRequestDTO = objectMapper.readValue(file.getInputStream(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, UnidadeCurricularRequestDTO.class));


        for (UnidadeCurricularRequestDTO unidadeCurricularRequestDTO : unidadesCurricularesRequestDTO) {
            if (unidadeCurricularRequestDTO.nome() != null && !unidadeCurricularRequestDTO.nome().isEmpty()) {

                UnidadeCurricular unidadeCurricular = mapper.paraEntidade(unidadeCurricularRequestDTO);
                repository.save(unidadeCurricular);
            }
        }
    }
}
