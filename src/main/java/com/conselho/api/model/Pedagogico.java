package com.conselho.api.model;

import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.model.usuario.UsuarioRole;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@Table(name = "pedagogico")
public class Pedagogico extends Usuario {

        public Pedagogico(String nome, String email, String senha, String role) {
                super(nome, email, senha, role);
        }
}
