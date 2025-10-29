package com.conselho.api.model.aluno;

import com.conselho.api.model.turma.Turma;
import com.conselho.api.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table
public class Aluno extends Usuario {

    @Column(name = "representante")
    private Boolean is_representative;

    @OneToOne
    @JoinColumn(name = "turma_id")
    private Turma turma;
    public Aluno(String nome, String email, String senha, boolean representative) {
    }
}
