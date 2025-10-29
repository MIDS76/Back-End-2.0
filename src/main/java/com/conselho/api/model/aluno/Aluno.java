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

    @ManyToOne
    @JoinColumn(name = "id_turma", nullable = true)
    private Turma turma;
    public Aluno(String nome, String email, String senha, boolean representative) {
    }
}
