package com.conselho.api.dto.mapper;

import com.conselho.api.dto.request.TurmaRequest;
import com.conselho.api.dto.response.TurmaResponse;
import com.conselho.api.model.turma.Turma;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TurmaMapper {

    public Turma paraEntidade(TurmaRequest request) {
        return new Turma(request.nome(), request.curso());
    }

    public TurmaResponse paraResposta(Turma turma, List<String> nomesAlunos) {
        return new TurmaResponse(turma.getId(), turma.getNome(), turma.getCurso(), nomesAlunos);
    }

    public Turma paraUpdate(TurmaRequest request, Turma turma) {
        if ((request.nome() != turma.getNome() && request.nome() != null)) {
            turma.setNome(request.nome());
        }
        if ((request.curso() != turma.getCurso() && request.curso() != null)) {
            turma.setCurso(request.curso());
        }
        return turma;
    }
}
