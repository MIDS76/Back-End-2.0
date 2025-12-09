package com.conselho.api.model.preConselho;

import com.conselho.api.model.conselho.Conselho;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PreConselho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_conselho")
    private Conselho conselho;

    // RELACIONAMENTO COM PRE CONSELHO AMBIENTE ENSINO
    @OneToMany(mappedBy = "preConselho")
    private List<PreConselhoAmbienteEnsino> preConselhoAmbienteEnsinos;

    // RELACIONAMENTO COM PRE CONSELHO PEDAGOGICO
    @OneToMany(mappedBy = "preConselho")
    private List<PreConselhoPedagogico> preConselhoPedagogicos;

    //RELACIONAMENTO COM PRE CONSELHO SUPERVISAO
    @OneToMany(mappedBy = "preConselho")
    private List<PreConselhoSupervisao> preConselhoSupervisoes;

//    RELACIONAMENTO COM PRE CONSELHO PROFESSOR
    @OneToMany(mappedBy = "preConselho", cascade = CascadeType.ALL)
    private List<PreConselhoProfessor> preConselhoProfessores;
}
