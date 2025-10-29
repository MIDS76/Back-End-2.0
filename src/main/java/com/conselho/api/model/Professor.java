package com.conselho.api.model;

import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.model.usuario.UsuarioRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@Data
@Table(name = "professor")
public class Professor extends Usuario {

    public Professor(String nome, String email, String senha, String role) {
        super(nome, email, senha, role);
    }
}
