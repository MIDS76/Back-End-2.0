package com.conselho.api.dto.mapper.entity;

import com.conselho.api.dto.request.entity.WegRequestDTO;
import com.conselho.api.dto.response.entity.WegResponseDTO;
import com.conselho.api.model.entity.Weg;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class WegMapper {
    public Weg paraEntidade(WegRequestDTO requestDTO, String senha){
        return new Weg(requestDTO.nome(), requestDTO.email(), senha);
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
        return weg; // Certifique-se de que não retorna null
    }
}
