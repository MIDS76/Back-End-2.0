package com.conselho.api.model.feedback;

import com.conselho.api.model.Aluno;
import com.conselho.api.model.Pedagogico;
import com.conselho.api.model.conselho.Conselho;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConselhoAlunoFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID CONSELHO RELACIONADO COM CLASSE CONSELHO - OK
    @ManyToOne
    @JoinColumn(name = "id_conselho", nullable = false)
    private Conselho conselho;

    // ID PEDAGOGICO RELACIONADO COM CLASSE PEDAGOGICO - OK
    @ManyToOne
    @JoinColumn(name = "id_pedagogico", nullable = false)
    private Pedagogico pedagogico;

    // ID ALUNO RELACIONADO COM CLASSE ALUNO - OK
    @ManyToOne
    @JoinColumn(name = "id_aluno", nullable = false)
    private Aluno aluno;

    @Lob
    private String pontosPositivos;

    @Lob
    private String pontosMelhoria;

    @Lob
    private String sugestao;
}
