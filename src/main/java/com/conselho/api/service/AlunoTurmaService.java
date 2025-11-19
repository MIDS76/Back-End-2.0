package com.conselho.api.service;

import com.conselho.api.dto.mapper.AlunoTurmaMapper;
import com.conselho.api.dto.request.AlunoTurmaRequestDTO;
import com.conselho.api.dto.response.AlunoTurmaResponseDTO;
import com.conselho.api.model.entity.Aluno;
import com.conselho.api.model.AlunoTurma;
import com.conselho.api.model.Turma;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.repository.entity.AlunoRepository;
import com.conselho.api.repository.AlunoTurmaRepository;
import com.conselho.api.repository.TurmaRepository;
import com.conselho.api.repository.entity.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AlunoTurmaService {

    private TurmaRepository turmaRepository;
    private AlunoRepository alunoRepository;
    private AlunoTurmaRepository alunoTurmaRepository;
    private AlunoTurmaMapper mapper;
    private UsuarioRepository usuarioRepository;

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
                alunoTurmas.get(0).getId(),  // PEGA O ID DO PRIMEIRO ALUNOTURMA DA LISTA
                turma.getNome(),
                nomeAlunos,
                alunoTurmas.get(0).isAtivo() // VERIFICA O STATUS DE ATIVO DO PRIMERIO ALUNOTURMA DA LISTA
        ));
    }

    public Map<String, Object> listarAlunosPorId(Long idTurma) {
        Turma turma = turmaRepository.findById(idTurma)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada."));

        List<AlunoTurma> alunoTurmas = alunoTurmaRepository.findByTurmaId(idTurma);

        List<Long> idAlunos = alunoTurmas.stream()
                .map(alunoTurma -> alunoTurma.getAluno().getId())
                .collect(Collectors.toList());

        List<Usuario> alunos = usuarioRepository.findAllById(idAlunos);

        List<Map<String, Object>> detalhesAlunos = alunos.stream()
                .map(usuario -> {
                    Map<String, Object> alunoMap = new HashMap<>();
                    alunoMap.put("id", usuario.getId());
                    alunoMap.put("nome", usuario.getNome());
                    alunoMap.put("email", usuario.getEmail());
                    alunoMap.put("statusAtividade", usuario.isAtivo() ? "Ativo" : "Inativo");
                    return alunoMap;
                })
                .collect(Collectors.toList());

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("nomeTurma", turma.getNome());
        resultado.put("statusTurma", turma.isAtivo() ? "Ativa" : "Inativa");
        resultado.put("alunos", detalhesAlunos);

        return resultado;
    }
}