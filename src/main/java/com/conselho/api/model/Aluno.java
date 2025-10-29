package com.conselho.api.model;

import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.model.usuario.UsuarioRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "aluno")
public class Aluno extends Usuario {

    @Column(name = "representante")
    private Boolean representante;

    @ManyToOne
    @JoinColumn(name = "turma_id") // Coluna que será usada para associar o aluno à turma
    private Turma turma;

    public Aluno(String nome, String email, String senha, String role, Boolean isRepresentative) {
        super(nome, email, senha, role);
        this.representante = isRepresentative;
    }
}
