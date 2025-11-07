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
@Table(name = "weg")
public class Weg extends Usuario {
    public Weg (String nome, String email, String senha){
        super(nome, email, senha, UsuarioRole.WEG);
    }
}
