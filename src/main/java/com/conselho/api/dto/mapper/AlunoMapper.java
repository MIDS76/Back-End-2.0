package com.conselho.api.dto.mapper;

import com.conselho.api.dto.request.AlunoRequest;
import com.conselho.api.dto.response.AlunoResponse;
import com.conselho.api.model.Aluno;
import org.springframework.stereotype.Component;

@Component
public class AlunoMapper {

//    public Aluno paraEntidade(AlunoRequest request){
//        //return new Aluno(request.nome(),request.email(),request.senha());
//    }

//    public AlunoResponse paraResposta(Aluno aluno){
//        return new AlunoResponse();
//    }

    public Aluno paraUpdate(AlunoRequest request,Aluno aluno){
//        if (()){
//            request.setNome(aluno.getNome());
//        }
        return aluno;
    }
}
