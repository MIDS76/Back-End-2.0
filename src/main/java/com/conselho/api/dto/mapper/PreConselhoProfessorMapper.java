package com.conselho.api.dto.mapper;

import com.conselho.api.dto.request.PreConselhoProfessorRequestDTO;
import com.conselho.api.dto.response.PreConselhoProfessorResponseDTO;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.professor.ProfessorNaoExisteException;
import com.conselho.api.exception.unidadeCurricular.UnidadeCurricularNaoExisteException;
import com.conselho.api.model.PreConselho;
import com.conselho.api.model.PreConselhoProfessor;
import com.conselho.api.model.Professor;
import com.conselho.api.model.UnidadeCurricular;
import com.conselho.api.repository.PreConselhoRepository;
import com.conselho.api.repository.ProfessorRepository;
import com.conselho.api.repository.UnidadeCurricularRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component

public class PreConselhoProfessorMapper {

    private ProfessorRepository professorRepository;
    private PreConselhoRepository preConselhoRepository;
    private UnidadeCurricularRepository unidadeCurricularRepository;

    public PreConselhoProfessor paraEntidade(PreConselhoProfessorRequestDTO requestDTO) {
        PreConselhoProfessor preConselhoProfessor = new PreConselhoProfessor();

        PreConselho preConselho = new PreConselho();
        preConselho.setId(requestDTO.idPreConselho());

        UnidadeCurricular unidadeCurricular = new UnidadeCurricular();
        unidadeCurricular.setId(requestDTO.idUnidadeCurricular());

        Professor professor = new Professor();
        professor.setId(requestDTO.idProfessor());

        preConselhoProfessor.setPreConselho(preConselho);
        preConselhoProfessor.setUnidadeCurricular(unidadeCurricular);
        preConselhoProfessor.setProfessor(professor);
        preConselhoProfessor.setPontosPositivos(preConselhoProfessor.getPontosPositivos());
        preConselhoProfessor.setPontosMelhoria(requestDTO.PontosMelhoria());
        preConselhoProfessor.setSugestao(preConselhoProfessor.getSugestao());


        return preConselhoProfessor;
    }

    public PreConselhoProfessorResponseDTO paraResponse(PreConselhoProfessor preConselhoProfessor) {
        return new PreConselhoProfessorResponseDTO(
                preConselhoProfessor.getId(),
                preConselhoProfessor.getPreConselho().getId(),
                preConselhoProfessor.getUnidadeCurricular().getId(),
                preConselhoProfessor.getUnidadeCurricular().getNome(),
                preConselhoProfessor.getProfessor().getId(),
                preConselhoProfessor.getProfessor().getNome(),
                preConselhoProfessor.getPontosPositivos(),
                preConselhoProfessor.getPontosMelhoria(),
                preConselhoProfessor.getSugestao());
    }

    public PreConselhoProfessor verificarUpdate(PreConselhoProfessorRequestDTO request, PreConselhoProfessor preConselhoProfessor) {

        if (request.idPreConselho() != null && (preConselhoProfessor.getPreConselho() == null || !request.idPreConselho().equals(preConselhoProfessor.getPreConselho().getId()))) {
            PreConselho preConselho = preConselhoRepository.findById(request.idPreConselho())
                    .orElseThrow(ConselhoNaoExiste::new);

            preConselhoProfessor.setPreConselho(preConselho);
        }

        if (request.idUnidadeCurricular() != null && (preConselhoProfessor.getUnidadeCurricular() == null || !request.idUnidadeCurricular().equals(preConselhoProfessor.getUnidadeCurricular().getId()))) {
            UnidadeCurricular unidadeCurricular = unidadeCurricularRepository.findById(request.idUnidadeCurricular())
                    .orElseThrow(UnidadeCurricularNaoExisteException::new);

            preConselhoProfessor.setUnidadeCurricular(unidadeCurricular);
        }

        if (request.idProfessor() != null && (preConselhoProfessor.getProfessor() == null || !request.idProfessor().equals(preConselhoProfessor.getProfessor().getId()))) {
            Professor professor = professorRepository.findById(request.idProfessor())
                    .orElseThrow(ProfessorNaoExisteException::new);

            preConselhoProfessor.setProfessor(professor);
        }
        if (request.PontosPositivos() != null && !request.PontosPositivos().equals(preConselhoProfessor.getPontosPositivos())) {
            preConselhoProfessor.setPontosPositivos(request.PontosPositivos());
        }
        if (request.PontosMelhoria() != null && !request.PontosMelhoria().equals(preConselhoProfessor.getPontosMelhoria())) {
            preConselhoProfessor.setPontosMelhoria(request.PontosMelhoria());
        }
        if (request.Sugestao() != null && !request.Sugestao().equals(preConselhoProfessor.getSugestao())) {
            preConselhoProfessor.setSugestao(request.Sugestao());
        }
        return preConselhoProfessor;
    }
}
