package com.conselho.api.controller;

import com.conselho.api.dto.request.ProfessorRequestDTO;
import com.conselho.api.dto.response.ProfessorResponseDTO;
import com.conselho.api.service.ProfessorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professores")
@AllArgsConstructor
public class ProfessorController {

    private final ProfessorService service;


    @GetMapping("/listar")
    public ResponseEntity<List<ProfessorResponseDTO>> listarProfessores(
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarProfessores());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ProfessorResponseDTO> buscarProfessorPorId(
            @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarProfessorPorId(id));
    }
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Void> atualizarProfessor(
            @PathVariable Long id,
            @RequestBody ProfessorRequestDTO professorRequest
    ){
        service.atualizarProfessor(id, professorRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarProfessor(
            @PathVariable Long id
    ){
        service.deletarProfessor(id);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}