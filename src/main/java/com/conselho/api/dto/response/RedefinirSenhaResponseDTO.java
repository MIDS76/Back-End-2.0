package com.conselho.api.dto.response;

import com.conselho.api.model.usuario.Usuario;

import java.time.LocalDateTime;

public record RedefinirSenhaResponseDTO(
        Long id,
        String token,
        LocalDateTime tempoExpiracao,
        Usuario usuario
){
}
