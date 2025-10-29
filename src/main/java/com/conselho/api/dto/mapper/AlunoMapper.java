package com.conselho.api.dto.mapper;

import com.conselho.api.dto.request.AlunoRequest;
import com.conselho.api.dto.response.AlunoResponse;
import com.conselho.api.model.Aluno;
import org.springframework.stereotype.Component;

@Component
public class AlunoMapper {

    public Aluno paraEntidade(AlunoRequest request){
        return new Aluno(request.nome(), request.email(), request.senha(), request.role(), request.representante());
    }

    public AlunoResponse paraResposta(Aluno aluno){
        return new AlunoResponse(aluno.getId(), aluno.getNome(), aluno.getEmail(), aluno.getSenha(), aluno.getRepresentante());
    }

    public Aluno paraUpdate(AlunoRequest request,Aluno aluno){
        if ((request.nome() != aluno.getNome() && request.nome() != null)){
            aluno.setNome(request.nome());
        }
        if ((request.email() != aluno.getEmail() && request.email() != null)){
            aluno.setEmail(request.email());
        }
        return aluno;
    }
}
