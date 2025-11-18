package com.conselho.api.controller;

import com.conselho.api.service.AtualizarService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/atualizar")
@AllArgsConstructor
public class AtualizarController {

    private AtualizarService service;

    @PatchMapping("/senha/{id}")
    public ResponseEntity<Void> atualizarSenha(
            @PathVariable Long id,
            @RequestBody Map<String, String> camposAtualizacao) {

        service.atualizarSenha(id, camposAtualizacao);
        return ResponseEntity.status(HttpStatus.OK)
                .build();

    }

    @PatchMapping("/representante/{id}")
    public ResponseEntity<Void> atualizarRepresentante(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> camposAtualizacao
    ){
        service.atualizarRepresentante(id, camposAtualizacao);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @PatchMapping("/atividade/{id}")
    public ResponseEntity<Void> atualizarAtividadeDoUsuario(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> camposAtualizacao
    ){
        service.atualizarAtividade(id, camposAtualizacao);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
