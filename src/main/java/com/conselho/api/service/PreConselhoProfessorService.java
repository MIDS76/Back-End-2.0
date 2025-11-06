package com.conselho.api.service;

import com.conselho.api.dto.mapper.PreConselhoProfessorMapper;
import com.conselho.api.dto.request.PreConselhoProfessorRequestDTO;
import com.conselho.api.dto.response.PreConselhoProfessorResponseDTO;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.preConselho.PreConselhoNaoExisteException;
import com.conselho.api.exception.preConselhoProfessor.PreConselhoProfessorNaoExisteException;
import com.conselho.api.exception.professor.ProfessorNaoExisteException;
import com.conselho.api.exception.unidadeCurricular.UnidadeCurricularNaoExisteException;
import com.conselho.api.model.PreConselhoProfessor;
import com.conselho.api.repository.PreConselhoProfessorRepository;
import com.conselho.api.repository.PreConselhoRepository;
import com.conselho.api.repository.UnidadeCurricularRepository;
import com.conselho.api.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PreConselhoProfessorService {

    @Autowired
    private PreConselhoProfessorMapper mapper;
    @Autowired
    private PreConselhoRepository preConselhoRepository;
    @Autowired
    private UnidadeCurricularRepository unidadeCurricularRepository;
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private PreConselhoProfessorRepository preConselhoProfessorRepository;

    public PreConselhoProfessorResponseDTO criarPreConselhoProfessor(PreConselhoProfessorRequestDTO request){
        PreConselhoProfessor preConselhoProfessor = mapper.paraEntidade(request);

        preConselhoProfessor.setPreConselho(preConselhoRepository.findById(request.idPreConselho())
                .orElseThrow(PreConselhoNaoExisteException::new));

        preConselhoProfessor.setUnidadeCurricular(unidadeCurricularRepository.findById(request.idUnidadeCurricular())
                .orElseThrow(UnidadeCurricularNaoExisteException::new));

        preConselhoProfessor.setProfessor(professorRepository.findById(request.idProfessor())
                .orElseThrow(ProfessorNaoExisteException::new));

        preConselhoProfessor.setPontosPositivos(preConselhoProfessor.getPontosPositivos());
        preConselhoProfessor.setPontosMelhoria(preConselhoProfessor.getPontosMelhoria());
        preConselhoProfessor.setSugestao(preConselhoProfessor.getSugestao());

        PreConselhoProfessor salvo = preConselhoProfessorRepository.save(preConselhoProfessor);
        return mapper.paraResponse(salvo);
    }

    public List<PreConselhoProfessorResponseDTO> listarPreConselhoProfessor(){
        return preConselhoProfessorRepository.findAll()
                .stream()
                .map(mapper::paraResponse)
                .toList();
    }

    public PreConselhoProfessorResponseDTO buscarPreConselhoProfessorPorId(Long id){
        PreConselhoProfessor preConselhoProfessor = preConselhoProfessorRepository.findById(id)
                .orElseThrow(PreConselhoProfessorNaoExisteException::new);

        return mapper.paraResponse(preConselhoProfessor);
    }

    public PreConselhoProfessorResponseDTO atualizarPreConselhoProfessor (Long id, PreConselhoProfessorRequestDTO request){
        PreConselhoProfessor preConselhoProfessor = preConselhoProfessorRepository.findById(id)
                .orElseThrow(PreConselhoNaoExisteException::new);

        PreConselhoProfessor preConcelhoProfessorAtualizado = mapper.verificarUpdate(request, preConselhoProfessor);

        return mapper.paraResponse(preConselhoProfessorRepository.save(preConcelhoProfessorAtualizado));
    }

    public void deletarConselho(Long id){
        if (!preConselhoProfessorRepository.existsById(id)){
            throw new ConselhoNaoExiste();
        }
        preConselhoProfessorRepository.deleteById(id);
    }
}
