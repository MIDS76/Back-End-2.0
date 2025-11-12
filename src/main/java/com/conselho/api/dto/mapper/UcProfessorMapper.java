package com.conselho.api.dto.mapper;

import com.conselho.api.dto.request.UcProfessorRequestDTO;
import com.conselho.api.dto.response.UcProfessorResponseDTO;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.professor.ProfessorNaoExisteException;
import com.conselho.api.exception.unidadeCurricular.UnidadeCurricularNaoExisteException;
import com.conselho.api.model.entity.Professor;
import com.conselho.api.model.UcProfessor;
import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.model.UnidadeCurricular;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.entity.ProfessorRepository;
import com.conselho.api.repository.UnidadeCurricularRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UcProfessorMapper {

    private ConselhoRepository conselhoRepository;
    private ProfessorRepository professorRepository;
    private UnidadeCurricularRepository unidadeCurricularRepository;

    public UcProfessor paraEntidade(UcProfessorRequestDTO request) {
        UcProfessor ucProfessor = new UcProfessor();

        Conselho conselho = new Conselho();
        conselho.setId(request.idConselho());

        Professor professor = new Professor();
        professor.setId(request.idProfessor());

        UnidadeCurricular unidadeCurricular = new UnidadeCurricular();
        unidadeCurricular.setId(request.idUnidadeCurricular());

        ucProfessor.setUnidadeCurricular(unidadeCurricular);
        ucProfessor.setConselho(conselho);
        ucProfessor.setProfessor(professor);

        return new UcProfessor();
    }

    public UcProfessorResponseDTO paraResposta(UcProfessor ucProfessor) {
        return new UcProfessorResponseDTO(
                ucProfessor.getId(),
                ucProfessor.getConselho().getId(),
                ucProfessor.getProfessor().getId(),
                ucProfessor.getProfessor().getNome(),
                ucProfessor.getUnidadeCurricular().getId(),
                ucProfessor.getUnidadeCurricular().getNome()
        );
    }

    public UcProfessor paraUpdate(UcProfessorRequestDTO request, UcProfessor ucProfessor) {

        if (request.idConselho() != null && (ucProfessor.getConselho() == null || !request.idConselho().equals(ucProfessor.getConselho().getId()))) {
            Conselho novoConselho = conselhoRepository.findById(request.idConselho())
                    .orElseThrow(ConselhoNaoExiste::new);
            ucProfessor.setConselho(novoConselho);
        }

        if (request.idProfessor() != null && (ucProfessor.getProfessor() == null || !request.idProfessor().equals(ucProfessor.getProfessor().getId()))) {
            Professor novoProfessor = professorRepository.findById(request.idProfessor())
                    .orElseThrow(ProfessorNaoExisteException::new);
            ucProfessor.setProfessor(novoProfessor);
        }

        if (request.idUnidadeCurricular() != null && (ucProfessor.getUnidadeCurricular() == null || !request.idUnidadeCurricular().equals(ucProfessor.getUnidadeCurricular().getId()))) {
            UnidadeCurricular novaUnidadeCurricular = unidadeCurricularRepository.findById(request.idUnidadeCurricular())
                    .orElseThrow(UnidadeCurricularNaoExisteException::new);
            ucProfessor.setUnidadeCurricular(novaUnidadeCurricular);
        }

        return ucProfessor;
    }
}
