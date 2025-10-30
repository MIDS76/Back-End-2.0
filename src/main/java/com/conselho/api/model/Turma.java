package com.conselho.api.model;

import com.conselho.api.model.Aluno;
import com.conselho.api.model.conselho.Conselho;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @OneToMany(mappedBy = "turma")
    private List<Aluno> listaNomesAlunos;

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conselho> conselhos;

    public Turma(String nome, String curso) {
        this.nome = nome;
        this.curso = curso;
    }
}
