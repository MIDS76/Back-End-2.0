package com.conselho.api.dto.mapper;

import com.conselho.api.dto.request.TurmaRequestDTO;
import com.conselho.api.dto.response.TurmaResponseDTO;
import com.conselho.api.model.entity.Turma;
import org.springframework.stereotype.Component;

@Component
public class TurmaMapper {

    public Turma paraEntidade(TurmaRequestDTO request) {
        Turma turma = new Turma();

        turma.setNome(request.nome());
        turma.setCurso(request.curso());
        turma.setDataInicio(request.dataInicio());
        turma.setDataFim(request.dataFinal());

        return turma;
    }

    public TurmaResponseDTO paraResposta(Turma turma) {
        return new TurmaResponseDTO(
                turma.getId(),
                turma.getNome(),
                turma.getCurso(),
                turma.getDataInicio(),
                turma.getDataFim());
    }

    public Turma paraUpdate(TurmaRequestDTO request, Turma turma) {
        if ((request.nome() != turma.getNome() && request.nome() != null)) {
            turma.setNome(request.nome());
        }
        if ((request.curso() != turma.getCurso() && request.curso() != null)) {
            turma.setCurso(request.curso());
        }
        if((request.dataInicio() != turma.getDataInicio() && request.dataInicio() !=  null)){
            turma.setDataInicio(request.dataInicio());
        }
        if((request.dataFinal() != turma.getDataFim() && request.dataFinal() !=  null)){
            turma.setDataFim(request.dataFinal());
        }
        return turma;
    }
}
