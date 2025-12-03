package com.conselho.api.dto.mapper.entity;

import com.conselho.api.dto.request.entity.AlunoRequestDTO;
import com.conselho.api.dto.request.entity.UsuarioRequestDTO;
import com.conselho.api.dto.response.entity.UsuarioResponseDTO;
import com.conselho.api.model.entity.Aluno;
import com.conselho.api.model.usuario.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioResponseDTO paraResposta(Usuario usuario){

        return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getRole(), usuario.isAtivo());
    }

    public Usuario paraUpdate(UsuarioRequestDTO request, Usuario usuario){
        if ((request.nome() != usuario.getNome() && request.nome() != null)){
            usuario.setNome(request.nome());
        }
        if ((request.email() != usuario.getEmail() && request.email() != null)){
            usuario.setEmail(request.email());
        }
        return usuario;
    }
}
