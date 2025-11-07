package com.conselho.api.model.entity;

import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.model.usuario.UsuarioRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "professor")
public class Professor extends Usuario {

    public Professor(String nome, String email, String senha) {
        super(nome, email, senha, UsuarioRole.PROFESSOR);
    }

    public Professor(Long id, String nome, String email, String senha, UsuarioRole role) {
        super(id, nome, email, senha, role);
    }
}
