package com.conselho.api.dto.mapper;

import com.conselho.api.dto.response.AlunoTurmaResponseDTO;
import com.conselho.api.model.AlunoTurma;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlunoTurmaMapper {

    public AlunoTurmaResponseDTO paraResposta(AlunoTurma alunoTurma, List<String> nomeAlunos) {
        return new AlunoTurmaResponseDTO(
                alunoTurma.getId(),        // ID do AlunoTurma
                alunoTurma.getTurma().getNome(),  // Nome da turma
                nomeAlunos,                // Lista de nomes dos alunos
                alunoTurma.isAtivo()       // Status ativo
        );
    }
}
