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

import java.sql.Struct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class UcProfessorMapper {

    private ConselhoRepository conselhoRepository;
    private ProfessorRepository professorRepository;
    private UnidadeCurricularRepository unidadeCurricularRepository;

    public UcProfessorResponseDTO paraRespostaComLista(UcProfessor ucProfessor, List<String> nomeUcs) {
        return new UcProfessorResponseDTO(
                ucProfessor.getId(),
                ucProfessor.getConselho().getId(),
                ucProfessor.getProfessor().getId(),
                ucProfessor.getProfessor().getNome(),
                nomeUcs);
    }

    public UcProfessorResponseDTO paraResposta(UcProfessor ucProfessor) {
        List<String> teste = new ArrayList<>();
        String teste2 = "teste";
        teste = null;
        return new UcProfessorResponseDTO(
                ucProfessor.getId(),
                ucProfessor.getConselho().getId(),
                ucProfessor.getProfessor().getId(),
                teste2,
                teste);

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


        return ucProfessor;
    }
}
