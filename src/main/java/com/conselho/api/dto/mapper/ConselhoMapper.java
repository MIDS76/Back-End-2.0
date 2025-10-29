package com.conselho.api.dto.mapper;

import com.conselho.api.dto.request.ConselhoRequest;
import com.conselho.api.model.conselho.Conselho;

public class ConselhoMapper {
    public Conselho paraEntidade(ConselhoRequest request){
        return new Conselho(
                request.idTurma(),
                request.dataInicio(),
                request.dataFim(),
                request.idRepresentante1(),
                request.idRepresentante2(),
                request.idPedagogico(),
                request.etapas());
        }
}