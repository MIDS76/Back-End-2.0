package com.conselho.api.controller;

import com.conselho.api.dto.request.ProfessorRequest;
import com.conselho.api.dto.request.SupervisorRequest;
import com.conselho.api.dto.response.ProfessorResponse;
import com.conselho.api.dto.response.SupervisorResponse;
import com.conselho.api.service.ProfessorService;
import com.conselho.api.service.SupervisorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supervisor")
@AllArgsConstructor
public class SupervisorController {

    private final SupervisorService service;

    @GetMapping
    public ResponseEntity<List<SupervisorResponse>> buscarTodosSupervisor(
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarSupervisores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupervisorResponse> buscarSupervisorPorId(
            @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarSupervisorPorId(id));
    }
    @PutMapping
    public ResponseEntity<SupervisorResponse> atualizarSupervisor(
            @PathVariable Long id,
            @RequestBody SupervisorRequest supervisorRequest
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarSupervisor(id, supervisorRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSupervisor(
            @PathVariable Long id
    ){
        service.deletarSupervisor(id);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
