package com.conselho.api.dto.mapper;

import com.conselho.api.dto.response.RedefinirSenhaResponseDTO;
import com.conselho.api.model.TokenRedefinicaoSenha;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RedefinirSenhaMapper {
    public RedefinirSenhaResponseDTO paraResposta(TokenRedefinicaoSenha tokenRedefinicaoSenha) {
        return new RedefinirSenhaResponseDTO(
                tokenRedefinicaoSenha.getId(),
                tokenRedefinicaoSenha.getToken(),
                tokenRedefinicaoSenha.getTempoExpiracao(),
                tokenRedefinicaoSenha.getUsuario()
        );
    }
}
