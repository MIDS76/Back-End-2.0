package com.conselho.api.dto.mapper.entity;

import com.conselho.api.dto.response.entity.UsuarioResponseDTO;
import com.conselho.api.model.usuario.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioResponseDTO paraResposta(Usuario usuario){

        return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getRole());
    }
}
