package com.conselho.api.dto.mapper.entity;

import com.conselho.api.dto.request.entity.ProfessorRequestDTO;
import com.conselho.api.dto.response.entity.ProfessorResponseDTO;
import com.conselho.api.model.entity.Professor;
import org.springframework.stereotype.Component;

@Component
public class ProfessorMapper {

    public Professor paraEntidade(ProfessorRequestDTO professorRequest, String senha){
        return new Professor(professorRequest.nome(), professorRequest.email(), senha);
    }

    public ProfessorResponseDTO paraRespostaProfessor(Professor professor){
        return new ProfessorResponseDTO(professor.getId(), professor.getNome(), professor.getEmail());
    }

    public Professor paraUpdate(ProfessorRequestDTO professorRequest, Professor professor){
        if((professorRequest.nome() != professor.getNome() && professorRequest.nome() != null)){
            professor.setNome(professorRequest.nome());
        }
        if((professorRequest.email() != professor.getEmail() && professorRequest.email() != null)){
            professor.setEmail(professorRequest.email());
        }
        return professor;
    }


}
