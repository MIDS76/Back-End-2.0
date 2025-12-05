package com.conselho.api.repository;

import com.conselho.api.model.TokenRedefinicaoSenha;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<TokenRedefinicaoSenha, Long> {

    TokenRedefinicaoSenha findByToken(String token);
}
