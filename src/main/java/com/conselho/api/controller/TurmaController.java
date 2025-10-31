package com.conselho.api.controller;

import com.conselho.api.dto.request.TurmaRequest;
import com.conselho.api.dto.response.TurmaResponse;
import com.conselho.api.service.TurmaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/turmas")
@RestController
@AllArgsConstructor
public class TurmaController {

    private TurmaService service;

    @GetMapping("/listar")
    public ResponseEntity<List<TurmaResponse>> buscarTodos(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarTurmas());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<TurmaResponse> buscarPorId(
            @PathVariable Long idTurma
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarTurmaPorId(idTurma));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<TurmaResponse> atualizarTurma(
            @PathVariable Long idTurma,
            @Valid @RequestBody TurmaRequest request
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarTurma(idTurma,request));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarTurma(
            @PathVariable Long idTurma
    ){
        service.deletarTurma(idTurma);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
