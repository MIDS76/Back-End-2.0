package com.conselho.api.service;

import com.conselho.api.dto.mapper.AlunoTurmaMapper;
import com.conselho.api.dto.request.AlunoTurmaRequestDTO;
import com.conselho.api.dto.response.AlunoTurmaResponseDTO;
import com.conselho.api.model.Aluno;
import com.conselho.api.model.AlunoTurma;
import com.conselho.api.model.Turma;
import com.conselho.api.repository.AlunoRepository;
import com.conselho.api.repository.AlunoTurmaRepository;
import com.conselho.api.repository.TurmaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AlunoTurmaService {

    private TurmaRepository turmaRepository;
    private AlunoRepository alunoRepository;
    private AlunoTurmaRepository alunoTurmaRepository;
    private AlunoTurmaMapper mapper;

    public List<AlunoTurmaResponseDTO> criarAlunoTurma(AlunoTurmaRequestDTO request) {
        Turma turma = turmaRepository.findById(request.idTurma())
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        List<Aluno> alunos = alunoRepository.findAllById(request.idsAlunos());

        List<AlunoTurma> alunoTurmas = alunos.stream()
                .map(aluno -> new AlunoTurma(turma, aluno))
                .collect(Collectors.toList());

        alunoTurmaRepository.saveAll(alunoTurmas);

        List<String> nomeAlunos = alunos.stream()
                .map(Aluno::getNome)
                .collect(Collectors.toList());

        return Collections.singletonList(new AlunoTurmaResponseDTO(
                alunoTurmas.get(0).getId(),  // Pega o ID do primeiro AlunoTurma criado
                turma.getNome(),             // Nome da turma
                nomeAlunos,                  // Lista de nomes dos alunos
                alunoTurmas.get(0).isAtivo() // Verifica o status de "ativo" da associação
        ));
    }

}