package com.conselho.api.dto.mapper;

import com.conselho.api.dto.request.ProfessorRequest;
import com.conselho.api.dto.response.ProfessorResponse;
import com.conselho.api.model.Professor;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

@Component
public class ProfessorMapper {

    public Professor paraEntidadeProfessor(ProfessorRequest professorRequest){
        return new Professor(professorRequest.nome(), professorRequest.email(), professorRequest.senha(),professorRequest.role());
    }

    public ProfessorResponse paraRespostaProfessor(Professor professor){
        return new ProfessorResponse(professor.getId(), professor.getNome(), professor.getEmail(), professor.getSenha());
    }

    public Professor paraUpdateProfessor(ProfessorRequest professorRequest, Professor professor){
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
