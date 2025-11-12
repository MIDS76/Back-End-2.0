package com.conselho.api.dto.mapper.entity;

import com.conselho.api.dto.request.entity.AlunoRequestDTO;
import com.conselho.api.dto.response.entity.AlunoResponseDTO;
import com.conselho.api.model.entity.Aluno;
import org.springframework.stereotype.Component;

@Component
public class AlunoMapper {


    public Aluno paraEntidade(AlunoRequestDTO request, String senha){
        return new Aluno(
                request.matricula(),
                request.nome(),
                request.email(),
                senha);
    }

    public AlunoResponseDTO paraResposta(Aluno aluno){

        return new AlunoResponseDTO(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getSenha(),
                aluno.isRepresentante());
    }

    public Aluno paraUpdate(AlunoRequestDTO request, Aluno aluno){
        if ((request.nome() != aluno.getNome() && request.nome() != null)){
            aluno.setNome(request.nome());
        }
        if ((request.email() != aluno.getEmail() && request.email() != null)){
            aluno.setEmail(request.email());
        }
        return aluno;
    }
}
