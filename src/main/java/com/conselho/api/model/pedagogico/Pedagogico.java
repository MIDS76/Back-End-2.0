package com.conselho.api.model.pedagogico;

import com.conselho.api.model.usuario.Usuario;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pedagogico extends Usuario {

        public Pedagogico(String nome, String email, String senha) {
        }
}
