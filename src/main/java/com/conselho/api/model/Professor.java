package com.conselho.api.model;

import com.conselho.api.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table
public class Professor extends Usuario {

    public Professor(String nome, String email, String senha) {
    }
}
