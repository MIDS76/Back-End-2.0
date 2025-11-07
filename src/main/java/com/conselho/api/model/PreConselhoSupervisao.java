package com.conselho.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class PreConselhoSupervisao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_preConselho")
    private PreConselho preConselho;

    @Column(nullable = false)
    private String pontosPositivos;

    @Column(nullable = false)
    private String pontosMelhoria;

    @Column(nullable = false)
    private String sugestoes;
}