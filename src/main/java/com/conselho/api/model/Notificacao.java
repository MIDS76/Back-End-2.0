package com.conselho.api.model;

import com.conselho.api.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Notificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;


    @Column(nullable = false)
    private String mensagem;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    private boolean lido = false;

    private Instant criadoEm = Instant.now();
}