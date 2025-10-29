package com.conselho.api.dto.mapper;

import com.conselho.api.dto.request.PedagogicoRequest;
import com.conselho.api.dto.response.PedagogicoResponse;
import com.conselho.api.model.Pedagogico;
import org.springframework.stereotype.Component;

@Component
public class PedagogicoMapper {
    public Pedagogico paraEntidade (PedagogicoRequest request){
        return new Pedagogico(request.nome(), request.email(), request.senha());
    }

    public PedagogicoResponse paraResposta (Pedagogico pedagogico){
        return new PedagogicoResponse(pedagogico.getId(), pedagogico.getNome(), pedagogico.getEmail(), pedagogico.getSenha());
    }

    public Pedagogico verificarUpdate(PedagogicoRequest request, Pedagogico pedagogico) {
        if (request.nome() != null && !request.nome().equals(pedagogico.getNome())){
            pedagogico.setNome(request.nome());
        }

        if (request.email() != null && !request.email().equals(pedagogico.getEmail())){
            pedagogico.setEmail(request.email());
        }

        if (request.senha() != null && !request.senha().equals(pedagogico.getSenha())){
            pedagogico.setSenha(request.senha());
        }
        return pedagogico;
    }
}