package com.conselho.api.dto.mapper;

import com.conselho.api.dto.request.AlunoTurmaRequestDTO;
import com.conselho.api.dto.response.AlunoTurmaResponseDTO;
import com.conselho.api.model.Aluno;
import com.conselho.api.model.AlunoTurma;
import com.conselho.api.model.Turma;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
