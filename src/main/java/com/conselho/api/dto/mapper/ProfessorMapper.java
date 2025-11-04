package com.conselho.api.dto.mapper;

import com.conselho.api.dto.request.ProfessorRequestDTO;
import com.conselho.api.dto.response.ProfessorResponseDTO;
import com.conselho.api.model.Professor;
import org.springframework.stereotype.Component;

@Component
public class ProfessorMapper {

    public Professor paraEntidadeProfessor(ProfessorRequestDTO professorRequest){
        return new Professor(professorRequest.nome(), professorRequest.email(), professorRequest.senha());
    }

    public ProfessorResponseDTO paraResposta(Professor professor){
        return new ProfessorResponseDTO(professor.getId(), professor.getNome(), professor.getEmail());
    }

    public Professor paraUpdate(ProfessorRequestDTO professorRequest, Professor professor){
        if((professorRequest.nome() != professor.getNome() && professorRequest.nome() != null)){
            professor.setNome(professorRequest.nome());
        }
        if((professorRequest.email() != professor.getEmail() && professorRequest.email() != null)){
            professor.setEmail(professorRequest.email());
        }
        if((professorRequest.senha() != professor.getSenha() && professorRequest.senha() != null)){
            professor.setSenha(professorRequest.senha());
        }
        return professor;
    }


}
