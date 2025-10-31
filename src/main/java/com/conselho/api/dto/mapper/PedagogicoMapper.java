package com.conselho.api.dto.mapper;

import com.conselho.api.dto.request.PedagogicoRequestDTO;
import com.conselho.api.dto.response.PedagogicoResponseDTO;
import com.conselho.api.model.Pedagogico;
import org.springframework.stereotype.Component;

@Component
public class PedagogicoMapper {
    public Pedagogico paraEntidade (PedagogicoRequestDTO request){
        return new Pedagogico(request.nome(), request.email(), request.senha());
    }

    public PedagogicoResponseDTO paraResposta (Pedagogico pedagogico){
        return new PedagogicoResponseDTO(pedagogico.getId(), pedagogico.getNome(), pedagogico.getEmail());
    }

    public Pedagogico paraUpdate(PedagogicoRequestDTO request, Pedagogico pedagogico) {
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