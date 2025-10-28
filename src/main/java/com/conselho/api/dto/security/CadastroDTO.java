package com.conselho.api.dto.security;

import com.conselho.api.model.usuario.UsuarioRole;

public record CadastroDTO(
        String nome,
        String email,
        String senha,
        UsuarioRole role
) {
}
