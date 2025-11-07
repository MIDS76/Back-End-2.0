package com.conselho.api.model;

import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.model.usuario.UsuarioRole;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "pedagogico")
public class Pedagogico extends Usuario {

    // RELACIONAMENTO COM CONSELHO
    @OneToMany(mappedBy = "pedagogico")
    private List<Conselho> conselhos;

    // RELACIONAMENTO COM CONSELHO ALUNO FEEDBACK
    @OneToMany(mappedBy = "pedagogico")
    private List<ConselhoAlunoFeedback> conselhoAlunoFeedbacks;

    public Pedagogico(String nome, String email, String senha) {
            super(nome, email, senha, UsuarioRole.PEDAGOGICO);
    }

    public Pedagogico(Long id, String nome, String email, String senha, UsuarioRole role, List<Conselho> conselhos) {
        super(id, nome, email, senha, role);
        this.conselhos = conselhos;
    }
}
