package com.conselho.api.model.preConselho;

import com.conselho.api.model.UnidadeCurricular;
import com.conselho.api.model.entity.Professor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
@Data
public class PreConselhoProfessor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "id_preConselho")
    private PreConselho preConselho;

    @ManyToOne
    @JoinColumn(name = "id_uc")
    private UnidadeCurricular unidadeCurricular;

    @ManyToOne
    @JoinColumn(name = "id_professor")
    private Professor professor;

    @Column()
    private String pontosPositivos;

    @Column()
    private String pontoMelhoria;

    @Column()
    private String sugestoes;


}