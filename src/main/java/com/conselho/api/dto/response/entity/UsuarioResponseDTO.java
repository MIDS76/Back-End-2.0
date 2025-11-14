package com.conselho.api.dto.response.entity;

import com.conselho.api.model.usuario.UsuarioRole;

public record UsuarioResponseDTO (
    Long id,
    String nome,
    String email,
    UsuarioRole role
){
}
