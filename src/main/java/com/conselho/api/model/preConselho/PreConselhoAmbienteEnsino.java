package com.conselho.api.model.preConselho;

import com.conselho.api.model.preConselho.PreConselho;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table
public class PreConselhoAmbienteEnsino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_PreConselho")
    private PreConselho preConselho;

    @Column(nullable = false)
    private String pontosPositivos;

    @Column(nullable = false)
    private String pontosMelhoria;

    @Column(nullable = false)
    private String sugestoes;
}
