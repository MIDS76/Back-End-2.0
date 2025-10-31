package com.conselho.api.dto.mapper;

import com.conselho.api.dto.request.ConselhoAlunoRequest;
import com.conselho.api.dto.response.ConselhoAlunoResponse;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.model.Aluno;
import com.conselho.api.model.ConselhoAluno;
import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.repository.ConselhoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConselhoAlunoMapper {

    private ConselhoRepository  conselhoRepository;
    public ConselhoAluno paraEntidade(ConselhoAlunoRequest request) {
        ConselhoAluno conselhoAluno = new ConselhoAluno();

        Conselho conselho = new Conselho();
        conselho.setId(request.idAluno());


        Aluno aluno = new Aluno();
        aluno.setId(request.idAluno());


        conselhoAluno.setConselho(conselho);
        conselhoAluno.setAluno(aluno);

        return conselhoAluno;
    }

    public ConselhoAlunoResponse paraResposta(ConselhoAluno conselhoAluno) {
        return new ConselhoAlunoResponse(
                conselhoAluno.getId(),
                conselhoAluno.getAluno().getId(),
                conselhoAluno.getAluno().getNome(),
                conselhoAluno.getConselho().getId()
        );
    }

    public ConselhoAluno verificarUpdate(ConselhoAlunoRequest request, ConselhoAluno conselhoAluno) {
        if (request.idAluno() != null && !request.idAluno().equals(conselhoAluno.getId())) {
            conselhoAluno.setId(request.idAluno());
        }
        if (request.idConselho() != null && !request.idConselho().equals(conselhoAluno.getConselho().getId())){
            Conselho conselho = conselhoRepository.findById(request.idConselho())
                            .orElseThrow(ConselhoNaoExiste::new);
            conselhoAluno.setConselho(conselho);

        }
        return conselhoAluno;
    }
}




