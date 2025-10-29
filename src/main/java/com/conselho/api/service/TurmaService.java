package com.conselho.api.service;

import com.conselho.api.dto.mapper.TurmaMapper;
import com.conselho.api.dto.request.TurmaRequest;
import com.conselho.api.dto.response.TurmaResponse;
import com.conselho.api.exception.turma.TurmaNaoExiste;
import com.conselho.api.model.turma.Turma;
import com.conselho.api.repository.TurmaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class TurmaService {

    private TurmaMapper mapper;
    private TurmaRepository repository;
    private final Map<Long, List<Long>> turmaMap;


    public TurmaResponse criarTurma(TurmaRequest request){
        List<String> nomesProf = repository.listaNomesAlunos(request.idAlunos());

        Turma newTurma = repository.save(mapper.paraEntidade(request));
        turmaMap.put(newTurma.getId(), request.idAlunos());

        return mapper.paraResposta(newTurma, nomesProf);
    }

    public List<TurmaResponse> buscarTurmas(){
        List<Turma> turmas = repository.findAll();
        List<TurmaResponse> respostaDTOS = new ArrayList<>();

        for (Turma turma : turmas){
            List<Long> idTurma = turmaMap.getOrDefault(turma.getId(),List.of());
            List<String> nomeTurma = new ArrayList<>();

            if (!idTurma.isEmpty()){
                nomeTurma = repository.listaNomesAlunos(idTurma);
            }
            respostaDTOS.add(mapper.paraResposta(turma,nomeTurma));
        }
        return respostaDTOS;
    }

    public TurmaResponse buscarTurmaPorId(Long idTurma){
        Turma turma = repository.findById(idTurma).orElseThrow(() ->
                new TurmaNaoExiste());

        List<Long> idAlunos = turmaMap.getOrDefault(turma.getId(), List.of());
        List<String> nomesAlunos = new ArrayList<>();

        if (!idAlunos.isEmpty()) {
            nomesAlunos = repository.listaNomesAlunos(idAlunos);
        }

        return mapper.paraResposta(turma, nomesAlunos);
    }


    public TurmaResponse atualizarTurma(Long idTurma, TurmaRequest request){
        Turma turma = repository.findById(idTurma)
                .orElseThrow(() -> new TurmaNaoExiste());

        Turma newTurma = mapper.paraUpdate(request, turma);
        repository.save(newTurma);

        List<Long> idAlunos = turmaMap.getOrDefault(newTurma.getId(), List.of());
        List<String> nomesAlunos = new ArrayList<>();

        if (!idAlunos.isEmpty()) {
            nomesAlunos = repository.listaNomesAlunos(idAlunos);
        }
        return mapper.paraResposta(newTurma, nomesAlunos);
    }


    public void deletarTurma(Long idTurma) {
        repository.findById(idTurma).orElseThrow(() ->
                new TurmaNaoExiste());

        repository.deleteById(idTurma);
    }
}