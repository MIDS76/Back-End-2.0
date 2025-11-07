package com.conselho.api.model;

import com.conselho.api.model.conselho.Conselho;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String curso;

    private boolean ativo = true;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conselho> conselhos;

    @OneToMany(mappedBy = "turma")
    private List<AlunoTurma> alunoTurmas = new ArrayList<>();

    public Turma(String nome, String curso, LocalDate dataInicio, LocalDate dataFim) {
        this.nome = nome;
        this.curso = curso;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }
}
