package com.conselho.api.model.entity;

import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.model.usuario.UsuarioRole;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@AllArgsConstructor
@Data
@Table(name = "admin")
public class Admin extends Usuario {

    public Admin(String nome, String email, String senha) {
        super(nome, email, senha, UsuarioRole.ADMIN);
    }
}
