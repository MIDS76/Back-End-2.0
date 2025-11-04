package com.conselho.api.model;

import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.model.usuario.UsuarioRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "aluno")
public class Aluno extends Usuario {

    @ManyToOne
    @JoinColumn(name = "turma_id") // Coluna que será usada para associar o aluno à turma
    private Turma turma;

    @Column(name = "representante")
    private boolean representante;

    public Aluno(String nome, String email, String senha, boolean representante) {
        super(nome, email, senha, UsuarioRole.ALUNO);
        this.representante = representante;
    }
}
