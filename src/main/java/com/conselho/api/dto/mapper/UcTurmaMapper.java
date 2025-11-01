package com.conselho.api.dto.mapper;

import com.conselho.api.dto.request.UcTurmaRequest;
import com.conselho.api.dto.response.UcTurmaResponse;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.professor.ProfessorNaoExisteException;
import com.conselho.api.exception.unidadeCurricular.UnidadeCurricularNaoExisteException;
import com.conselho.api.model.Professor;
import com.conselho.api.model.UcTurma;
import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.model.UnidadeCurricular;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.ProfessorRepository;
import com.conselho.api.repository.UnidadeCurricularRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UcTurmaMapper {

    private ConselhoRepository conselhoRepository;
    private ProfessorRepository professorRepository;
    private UnidadeCurricularRepository unidadeCurricularRepository;

    public UcTurma paraEntidade(UcTurmaRequest request) {
        UcTurma ucTurma = new UcTurma();

        Conselho conselho = new Conselho();
        conselho.setId(request.idConselho());

        Professor professor = new Professor();
        professor.setId(request.idProfessor());

        UnidadeCurricular unidadeCurricular = new UnidadeCurricular();
        unidadeCurricular.setId(request.idUnidadeCurricular());

        ucTurma.setUnidadeCurricular(unidadeCurricular);
        ucTurma.setConselho(conselho);
        ucTurma.setProfessor(professor);

        return new UcTurma();
    }

    public UcTurmaResponse paraResposta(UcTurma ucTurma) {
        return new UcTurmaResponse(
                ucTurma.getId(),
                ucTurma.getConselho().getId(),
                ucTurma.getProfessor().getId(),
                ucTurma.getProfessor().getNome(),
                ucTurma.getUnidadeCurricular().getId(),
                ucTurma.getUnidadeCurricular().getNome()
        );
    }

    public UcTurma paraAtualizar(UcTurmaRequest request, UcTurma ucTurma) {

        if (request.idConselho() != null && (ucTurma.getConselho() == null || !request.idConselho().equals(ucTurma.getConselho().getId()))) {
            Conselho novoConselho = conselhoRepository.findById(request.idConselho())
                    .orElseThrow(ConselhoNaoExiste::new);
            ucTurma.setConselho(novoConselho);
        }

        if (request.idProfessor() != null && (ucTurma.getProfessor() == null || !request.idProfessor().equals(ucTurma.getProfessor().getId()))) {
            Professor novoProfessor = professorRepository.findById(request.idProfessor())
                    .orElseThrow(ProfessorNaoExisteException::new);
            ucTurma.setProfessor(novoProfessor);
        }

        if (request.idUnidadeCurricular() != null && (ucTurma.getUnidadeCurricular() == null || !request.idUnidadeCurricular().equals(ucTurma.getUnidadeCurricular().getId()))) {
            UnidadeCurricular novaUnidadeCurricular = unidadeCurricularRepository.findById(request.idUnidadeCurricular())
                    .orElseThrow(UnidadeCurricularNaoExisteException::new);
            ucTurma.setUnidadeCurricular(novaUnidadeCurricular);
        }

        return ucTurma;
    }
}
