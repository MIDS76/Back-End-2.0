package com.conselho.api.controller;

import com.conselho.api.dto.request.ProfessorRequest;
import com.conselho.api.dto.response.ProfessorResponse;
import com.conselho.api.service.ProfessorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professor")
@AllArgsConstructor
public class ProfessorController {

    private final ProfessorService service;

    @PostMapping
    public ResponseEntity<ProfessorResponse> postProfessor(
            @RequestBody ProfessorRequest professorRequest
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criarProfessor(professorRequest));
    }

    @GetMapping
    public ResponseEntity<List<ProfessorResponse>> buscarTodosProfessores(
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarProfessores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponse> buscarProfessorPorId(
            @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarProfessorPorId(id));
    }
    @PutMapping
    public ResponseEntity<ProfessorResponse> atualizarProfessor(
            @PathVariable Long id,
            @RequestBody ProfessorRequest professorRequest
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarProfessor(id, professorRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProfessor(
            @PathVariable Long id
    ){
        service.deletarProfessor(id);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}