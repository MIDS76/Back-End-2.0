package com.conselho.api.model.conselho;

import com.conselho.api.model.Aluno;
import com.conselho.api.model.ConselhoProfessor;
import com.conselho.api.model.Pedagogico;
import com.conselho.api.model.Turma;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EtapasConselho etapas = EtapasConselho.NAO_INICIADO;

    // vou criar uma relação de conselho para muitos conselhosProfessores
    @OneToMany(mappedBy = "conselho")
    private List<ConselhoProfessor> conselhoProfessores;
}
