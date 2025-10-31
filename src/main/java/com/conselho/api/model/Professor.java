package com.conselho.api.model;

import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.model.usuario.UsuarioRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@Data
@Table(name = "professor")
public class Professor extends Usuario {

    @OneToMany(mappedBy = "professor")
    private List<ConselhoProfessor> conselhoProfessores;

    public Professor(String nome, String email, String senha, String role) {
        super(nome, email, senha, role);
    }
}
