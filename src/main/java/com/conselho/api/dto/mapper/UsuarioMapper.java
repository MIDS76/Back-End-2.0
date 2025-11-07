package com.conselho.api.dto.mapper;

import com.conselho.api.dto.response.AlunoResponseDTO;
import com.conselho.api.dto.response.UsuarioResponseDTO;
import com.conselho.api.model.Aluno;
import com.conselho.api.model.usuario.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioResponseDTO paraResposta(Usuario usuario){

        return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}
