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
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pedagogico extends Usuario {

        @OneToMany(mappedBy = "pedagogico")
        private List<Conselho> conselhos;

        public Pedagogico(String nome, String email, String senha, String role) {
            super(nome, email, senha, role);
        }
}
