package com.conselho.api.dto.mapper;

import com.conselho.api.dto.request.AlunoRequestDTO;
import com.conselho.api.dto.response.AlunoResponseDTO;
import com.conselho.api.model.Aluno;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AlunoMapper {


    public Aluno paraEntidade(AlunoRequestDTO request){
        String senha = new BCryptPasswordEncoder().encode(request.senha());
        return new Aluno(request.nome(), request.email(), senha, request.representante());
    }

    public AlunoResponseDTO paraResposta(Aluno aluno){

        return new AlunoResponseDTO(aluno.getId(), aluno.getNome(), aluno.getEmail(), aluno.getSenha(), aluno.isRepresentante());
    }

    public Aluno paraUpdate(AlunoRequestDTO request, Aluno aluno){
        if ((request.nome() != aluno.getNome() && request.nome() != null)){
            aluno.setNome(request.nome());
        }
        if ((request.email() != aluno.getEmail() && request.email() != null)){
            aluno.setEmail(request.email());
        }

        if((request.representante() != aluno.isRepresentante() && request != null)){
            aluno.setRepresentante(request.representante());
        }
        return aluno;
    }
}
