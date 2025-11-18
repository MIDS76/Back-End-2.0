package com.conselho.api.service.preConselho;

import com.conselho.api.dto.mapper.preConselho.PreConselhoProfessorMapper;
import com.conselho.api.dto.request.preConselho.PreConselhoProfessorRequestDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoProfessorResponseDTO;
import com.conselho.api.exception.preConselho.PreConselhoNaoExisteException;
import com.conselho.api.exception.preConselhoProfessor.PreConselhoProfessorNaoExisteException;
import com.conselho.api.exception.professor.ProfessorNaoExisteException;
import com.conselho.api.exception.unidadeCurricular.UnidadeCurricularNaoExisteException;
import com.conselho.api.model.preConselho.PreConselhoProfessor;
import com.conselho.api.repository.PreConselhoProfessorRepository;
import com.conselho.api.repository.UnidadeCurricularRepository;
import com.conselho.api.repository.entity.ProfessorRepository;
import com.conselho.api.repository.preConselho.PreConselhoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class PreConselhoProfessorService {

    private PreConselhoProfessorMapper mapper;
    private PreConselhoRepository preConselhoRepository;
    private UnidadeCurricularRepository unidadeCurricularRepository;
    private ProfessorRepository professorRepository;
    private PreConselhoProfessorRepository preConselhoProfessorRepository;

    public PreConselhoProfessorResponseDTO
    criarPreConselhoProfessor(PreConselhoProfessorRequestDTO request){
        PreConselhoProfessor preConselhoProfessor = mapper.paraEntidade(request);

        preConselhoProfessor.setPreConselho(preConselhoRepository.findById(request.idPreConselho())
                .orElseThrow(PreConselhoProfessorNaoExisteException::new));

        preConselhoProfessor.setUnidadeCurricular(unidadeCurricularRepository.findById(request.idUnidadeCurricular())
                .orElseThrow(UnidadeCurricularNaoExisteException::new));

            preConselhoProfessor.setProfessor(professorRepository.findById(request.idProfessor())
                .orElseThrow(ProfessorNaoExisteException::new));

        preConselhoProfessor.setPontosPositivos(request.pontosPositivos());
        preConselhoProfessor.setPontoMelhoria(request.pontoMelhoria());
        preConselhoProfessor.setSugestoes(request.sugestoes());

        PreConselhoProfessor salvo = preConselhoProfessorRepository.save(preConselhoProfessor);
        return mapper.paraResposta(salvo);
    }

    public List<PreConselhoProfessorResponseDTO> listarPreConselhoProfessor(){
        return preConselhoProfessorRepository.findAll()
                .stream()
                .map(mapper::paraResposta)
                .toList();
    }

    public PreConselhoProfessorResponseDTO buscarPreConselhoProfessorPorId(Long id){
        PreConselhoProfessor preConselhoProfessor = preConselhoProfessorRepository.findById(id)
                .orElseThrow(PreConselhoProfessorNaoExisteException::new);

        return mapper.paraResposta(preConselhoProfessor);
    }

    public PreConselhoProfessorResponseDTO atualizarPreConselhoProfessor (Long id, PreConselhoProfessorRequestDTO request){
        PreConselhoProfessor preConselhoProfessor = preConselhoProfessorRepository.findById(id)
                .orElseThrow(PreConselhoNaoExisteException::new);

        PreConselhoProfessor preConcelhoProfessorAtualizado = mapper.paraUpdate(request, preConselhoProfessor);

        return mapper.paraResposta(preConselhoProfessorRepository.save(preConcelhoProfessorAtualizado));
    }

    public void deletarPreConselhoProfessor(Long id){
        if (!preConselhoProfessorRepository.existsById(id)){
            throw new PreConselhoProfessorNaoExisteException();
        }
        preConselhoProfessorRepository.deleteById(id);
    }
}
