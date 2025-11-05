package com.conselho.api.dto.mapper;

import com.conselho.api.dto.request.ProfessorRequestDTO;
import com.conselho.api.dto.response.ProfessorResponseDTO;
import com.conselho.api.model.Professor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ProfessorMapper {

    public Professor paraEntidade(ProfessorRequestDTO professorRequest){
        return new Professor(professorRequest.nome(), professorRequest.email(), professorRequest.senha());
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
        if((professorRequest.senha() != professor.getSenha() && professorRequest.senha() != null)){
            String senhaCriptografada = new BCryptPasswordEncoder().encode(professorRequest.senha());
            professor.setSenha(senhaCriptografada);
            professor.setSenha(professorRequest.senha());
        }
        return professor;
    }


}
