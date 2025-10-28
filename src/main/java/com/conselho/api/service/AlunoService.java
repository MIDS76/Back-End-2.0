package com.conselho.api.service;

import com.conselho.api.dto.mapper.AlunoMapper;
import com.conselho.api.dto.response.AlunoResponse;
import com.conselho.api.exception.aluno.AlunoNaoExisteException;
import com.conselho.api.exception.aluno.AlunoJaExisteException;
import com.conselho.api.model.aluno.Aluno;
import com.conselho.api.repository.AlunoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AlunoService {

    private final AlunoRepository repository;

    private final AlunoMapper mapper;

    public AlunoResponse criarAluno(Aluno aluno){
        if (repository.existsByNome(aluno.getNome())) {
            throw new AlunoJaExisteException();
        }
        return mapper.paraResposta(repository.save(aluno));
    }

    public List<AlunoResponse> buscarAlunos(){
        return repository.findAll().stream()
                .map(mapper::paraResposta)
                .toList();
    }

    public AlunoResponse buscarAlunoPorId(Long idAluno){
        return mapper.paraResposta(repository.findById(idAluno)
                .orElseThrow(() -> {
                    throw new AlunoNaoExisteException();
                }));
    }

    public AlunoResponse atualizarAluno(Long idAluno, Aluno aluno){
        if (!repository.existsByNome(aluno.getNome())) {
            throw new AlunoJaExisteException();
        }
        aluno.setId(idAluno);
        return mapper.paraResposta(repository.save(aluno));
    }

    public AlunoResponse deletarAluno(Long idAluno) {
        Aluno aluno = repository.findById(idAluno).orElseThrow(() ->
                new AlunoNaoExisteException());

        repository.deleteById(idAluno);

        return mapper.paraResposta(aluno);
    }

    public boolean isRepresentante(Long idAluno) {
        return repository.existsByIdAndRepresentanteTrue(idAluno);
    }

    public Aluno getRepresentante() {
        return repository.findByRepresentanteTrue();
    }
}
