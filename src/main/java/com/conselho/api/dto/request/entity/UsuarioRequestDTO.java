package com.conselho.api.dto.request.entity;

import com.conselho.api.model.usuario.UsuarioRole;

public record UsuarioRequestDTO (
        String nome,
        String email,
        boolean ativo
){
}
