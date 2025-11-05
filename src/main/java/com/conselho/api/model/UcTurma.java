package com.conselho.api.model;

import com.conselho.api.model.conselho.Conselho;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class UcTurma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_conselho")
    private Conselho conselho;

    @ManyToOne
    @JoinColumn(name = "id_professor")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "id_unidade_curricular")
    private UnidadeCurricular unidadeCurricular;
}
