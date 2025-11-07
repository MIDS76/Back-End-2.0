package com.conselho.api.model;


import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.model.usuario.UsuarioRole;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "supervisor")
public class Supervisor extends Usuario {
    public Supervisor( String nome, String email, String senha) {
        super(nome, email, senha, UsuarioRole.SUPERVISOR);
    }

    public Supervisor(Long id, String nome, String email, String senha, UsuarioRole role) {
        super(id, nome, email, senha, role);
    }
}
