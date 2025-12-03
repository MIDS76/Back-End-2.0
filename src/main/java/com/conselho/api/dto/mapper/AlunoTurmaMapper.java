package com.conselho.api.dto.mapper;

import com.conselho.api.dto.response.AlunoTurmaResponseDTO;
import com.conselho.api.model.AlunoTurma;
import com.conselho.api.model.entity.Aluno;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlunoTurmaMapper {

    public Aluno paraResposta(AlunoTurma alunoTurma) {
        return alunoTurma.getAluno();
    }
}
