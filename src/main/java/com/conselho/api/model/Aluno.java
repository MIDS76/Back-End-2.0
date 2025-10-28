package com.conselho.api.model;

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
    @JoinColumn(name = "conselho_id")
    private Conselho conselho;
    public Aluno(String nome, String email, String senha, boolean representative) {
    }
}
