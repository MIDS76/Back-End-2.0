package com.conselho.api.dto.mapper.preConselho;

import com.conselho.api.dto.request.AtualizarPreConselhoProfessorRequestDTO;
import com.conselho.api.dto.request.preConselho.PreConselhoProfessorRequestDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoProfessorResponseDTO;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.professor.ProfessorNaoExisteException;
import com.conselho.api.exception.unidadeCurricular.UnidadeCurricularNaoExisteException;
import com.conselho.api.model.entity.Professor;
import com.conselho.api.model.preConselho.PreConselho;
import com.conselho.api.model.preConselho.PreConselhoProfessor;
import com.conselho.api.model.UnidadeCurricular;
import com.conselho.api.repository.PreConselhoProfessorRepository;
import com.conselho.api.repository.UnidadeCurricularRepository;
import com.conselho.api.repository.entity.ProfessorRepository;
import com.conselho.api.repository.preConselho.PreConselhoRepository;
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
        preConselhoProfessor.setPontoMelhoria(requestDTO.pontoMelhoria());
        preConselhoProfessor.setSugestoes(preConselhoProfessor.getSugestoes());


        return preConselhoProfessor;
    }

    public PreConselhoProfessorResponseDTO paraResposta(PreConselhoProfessor preConselhoProfessor) {
        return new PreConselhoProfessorResponseDTO(
                preConselhoProfessor.getId(),
                preConselhoProfessor.getPreConselho().getId(),
                preConselhoProfessor.getUnidadeCurricular().getId(),
                preConselhoProfessor.getUnidadeCurricular().getNome(),
                preConselhoProfessor.getProfessor().getId(),
                preConselhoProfessor.getProfessor().getNome(),
                preConselhoProfessor.getPontosPositivos(),
                preConselhoProfessor.getPontoMelhoria(),
                preConselhoProfessor.getSugestoes());
    }

    public PreConselhoProfessor paraUpdate(AtualizarPreConselhoProfessorRequestDTO request, PreConselhoProfessor preConselhoProfessor) {

        if (request.pontosPositivos() != null && !request.pontosPositivos().equals(preConselhoProfessor.getPontosPositivos())) {
            preConselhoProfessor.setPontosPositivos(request.pontosPositivos());
        }
        if (request.oportunidadeMelhoria() != null && !request.oportunidadeMelhoria().equals(preConselhoProfessor.getPontoMelhoria())) {
            preConselhoProfessor.setPontoMelhoria(request.oportunidadeMelhoria());
        }
        if (request.sugestoes() != null && !request.sugestoes().equals(preConselhoProfessor.getSugestoes())) {
            preConselhoProfessor.setSugestoes(request.sugestoes());
        }
        return preConselhoProfessor;
    }
}
