package com.conselho.api.model.entity;

import com.conselho.api.model.AlunoTurma;
import com.conselho.api.model.feedback.ConselhoAlunoFeedback;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.model.usuario.UsuarioRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "aluno")
public class Aluno extends Usuario {

    @Column(nullable = false, unique = true)
    private String matricula;

    @Column(name = "representante")
    private boolean representante;

    @OneToMany(mappedBy = "aluno")
    private List<AlunoTurma> alunoTurmas = new ArrayList<>();

    // RELACIONAMENTO COM CONSELHO ALUNO FEEDBACK
    @OneToMany(mappedBy = "aluno")
    private List<ConselhoAlunoFeedback> conselhoAlunoFeedbacks;

    public Aluno(String nome, String email, String senha, String matricula) {
        super(nome, email, senha, UsuarioRole.ALUNO);
        this.matricula = matricula;
    }

    public Aluno(String nome, String email, String senha, String matricula, boolean representante, boolean primeiroAcesso) {
        super(nome, email, senha, UsuarioRole.ALUNO);
        this.matricula = matricula;
        this.representante = representante;
    }
}
