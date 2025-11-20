package com.conselho.api.model;

import com.conselho.api.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TokenRedefinicaoSenha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private LocalDateTime tempoExpiracao;

    @ManyToOne
    private Usuario usuario;

    public TokenRedefinicaoSenha(String token,LocalDateTime tempoExpiracao,Usuario usuario) {
        this.token = token;
        this.tempoExpiracao = tempoExpiracao;
        this.usuario = usuario;
    }
}
