package com.conselho.api.model;

import com.conselho.api.model.entity.Aluno;
import com.conselho.api.model.entity.Turma;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "aluno_turma")
public class AlunoTurma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "id_aluno", nullable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "id_turma", nullable = false)
    private Turma turma;


    private boolean ativo;

    public AlunoTurma(Turma turma, Aluno aluno) {
        this.aluno = aluno;
        this.turma = turma;
        this.ativo = true;
    }

}
