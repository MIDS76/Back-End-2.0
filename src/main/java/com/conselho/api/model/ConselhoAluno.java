package com.conselho.api.model;

import com.conselho.api.model.conselho.Conselho;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table
public class ConselhoAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "id_aluno")
    private Aluno aluno;

    @OneToMany
    @JoinColumn(name = "id_conselho")
    private Conselho conselho;

    public ConselhoAluno(Aluno aluno, Conselho conselho) {
        this.aluno = aluno;
        this.conselho = conselho;
    }
}
