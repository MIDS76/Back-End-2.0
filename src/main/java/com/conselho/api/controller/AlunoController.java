package com.conselho.api.controller;

import com.conselho.api.dto.response.AlunoResponse;
import com.conselho.api.model.Aluno;
import com.conselho.api.service.AlunoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/alunos")
@RestController
@AllArgsConstructor
public class AlunoController {

    private final AlunoService service;

    @PutMapping("/criar")
    public ResponseEntity<AlunoResponse> criarAluno(
            @Valid @RequestBody Aluno aluno
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criarAluno(aluno));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<AlunoResponse>> obterTodos(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarAlunos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<AlunoResponse> obterAlunoPorId(
            @PathVariable Long idAluno
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarAlunoPorId(idAluno));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<AlunoResponse> atualizarAluno(
            @PathVariable Long id,
            @Valid @RequestBody Aluno aluno
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarAluno(id,aluno));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<AlunoResponse> deletarAluno(
            @PathVariable Long idAluno
    ){
        service.deletarAluno(idAluno);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

}
