package com.conselho.api.dto.mapper;

import com.conselho.api.dto.request.ConselhoProfessorRequest;
import com.conselho.api.dto.response.ConselhoProfessorResponse;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.professor.ProfessorNaoExisteException;
import com.conselho.api.model.ConselhoProfessor;
import com.conselho.api.model.Professor;
import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.ProfessorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConselhoProfessorMapper {
    private ProfessorRepository professorRepository;
    private ConselhoRepository conselhoRepository;

    public ConselhoProfessor paraEntidade(ConselhoProfessorRequest request){
        ConselhoProfessor conselhoProfessor = new ConselhoProfessor();

        Conselho conselho = new Conselho();
        conselho.setId(request.idConselho());

        Professor professor = new Professor();
        professor.setId(request.idProfessor());

        conselhoProfessor.setConselho(conselho);
        conselhoProfessor.setProfessor(professor);

        return conselhoProfessor;
    }

    public ConselhoProfessorResponse paraResposta(ConselhoProfessor conselhoProfessor){
        return new ConselhoProfessorResponse(
                conselhoProfessor.getId(),
                conselhoProfessor.getConselho().getId(),
                conselhoProfessor.getProfessor().getId(),
                conselhoProfessor.getProfessor().getNome()
        );
    }

    public ConselhoProfessor verificarUpdate(ConselhoProfessorRequest request, ConselhoProfessor conselhoProfessor){
        if (request.idConselho() != null && (conselhoProfessor.getConselho() == null ||
                !request.idConselho().equals(conselhoProfessor.getConselho().getId()))){

            Conselho conselho = conselhoRepository.findById(request.idConselho())
                    .orElseThrow(ConselhoNaoExiste::new);

            conselhoProfessor.setConselho(conselho);
        }

        if (request.idProfessor() != null && (conselhoProfessor.getProfessor() == null ||
                !request.idProfessor().equals(conselhoProfessor.getConselho().getId()))){

            Professor professor = professorRepository.findById(request.idProfessor())
                    .orElseThrow(ProfessorNaoExisteException::new);

            conselhoProfessor.setProfessor(professor);
        }

        return conselhoProfessor;
    }
}
