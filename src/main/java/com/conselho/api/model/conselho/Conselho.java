package com.conselho.api.model.conselho;

import com.conselho.api.model.*;
import com.conselho.api.model.feedback.ConselhoAlunoFeedback;
import com.conselho.api.model.feedback.ConselhoTurmaFeedback;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Conselho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_turma")
    private Turma turma;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @ManyToOne
    @JoinColumn(name = "id_representante1")
    private Aluno representante1;

    @ManyToOne
    @JoinColumn(name = "id_representante2")
    private Aluno representante2;

    @ManyToOne
    @JoinColumn(name = "id_pedagogico")
    private Pedagogico pedagogico;

    // RELACIONAMENTO COM CONSELHO ALUNO FEEDBACK
    @OneToMany(mappedBy = "conselho")
    private List<ConselhoAlunoFeedback> conselhoAlunoFeedback;

    // RELACIONAMENTO COM CONSELHO TURMA FEEDBACK
    @OneToMany(mappedBy = "conselho")
    private List<ConselhoTurmaFeedback> conselhoTurmaFeedbacks;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EtapasConselho etapas = EtapasConselho.NAO_INICIADO;
}
