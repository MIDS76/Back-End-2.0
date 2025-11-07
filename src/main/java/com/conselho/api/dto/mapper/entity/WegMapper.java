package com.conselho.api.dto.mapper.entity;

import com.conselho.api.dto.request.entity.WegRequestDTO;
import com.conselho.api.dto.response.entity.WegResponseDTO;
import com.conselho.api.model.entity.Weg;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class WegMapper {
    public Weg paraEntidade(WegRequestDTO requestDTO){
        return new Weg(requestDTO.nome(), requestDTO.email(), requestDTO.senha());
    }

    public WegResponseDTO paraResposta(Weg weg){
        return new WegResponseDTO(weg.getId(), weg.getNome(), weg.getEmail());
    }

    public Weg paraUpdate(WegRequestDTO requestDTO, Weg weg){
        if(requestDTO.nome() != null && !requestDTO.nome().equals(weg.getNome())){
            weg.setNome(requestDTO.nome());
        }
        if(requestDTO.email() != null && !requestDTO.email().equals(weg.getEmail())){
            weg.setEmail(requestDTO.email());
        }
        if((requestDTO.senha() != weg.getSenha() && requestDTO.senha() != null)){
            String senhaCriptografada = new BCryptPasswordEncoder().encode(requestDTO.senha());
            weg.setSenha(senhaCriptografada);
            weg.setSenha(requestDTO.senha());
        }
        return weg;
    }
}
