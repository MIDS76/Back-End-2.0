package com.conselho.api.controller;

import com.conselho.api.dto.request.SupervisorRequestDTO;
import com.conselho.api.dto.response.SupervisorResponse;
import com.conselho.api.service.SupervisorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supervisor")
@AllArgsConstructor
public class SupervisorController {

    private final SupervisorService service;

    @GetMapping("/listar")
    public ResponseEntity<List<SupervisorResponse>> buscarTodosSupervisor(
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarSupervisores());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<SupervisorResponse> buscarSupervisorPorId(
            @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarSupervisorPorId(id));
    }
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Void> atualizarSupervisor(
            @PathVariable Long id,
            @RequestBody SupervisorRequestDTO supervisorRequestDTO
    ){
        service.atualizarSupervisor(id, supervisorRequestDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarSupervisor(
            @PathVariable Long id
    ){
        service.deletarSupervisor(id);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
