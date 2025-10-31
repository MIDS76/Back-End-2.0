package com.conselho.api.controller;

import com.conselho.api.dto.request.ConselhoProfessorRequest;
import com.conselho.api.dto.response.ConselhoProfessorResponse;
import com.conselho.api.dto.response.ProfessorResponse;
import com.conselho.api.service.ConselhoProfessorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/conselhoProfessor")
public class ConselhoProfessorController {
    private ConselhoProfessorService service;

    @PostMapping("/criar")
    public ResponseEntity<ConselhoProfessorResponse> create (@RequestBody @Valid ConselhoProfessorRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarConselhoProfessor(request));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ConselhoProfessorResponse>> buscarTodos(){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarTodos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ConselhoProfessorResponse> buscarPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorId(id));
    }

    // BUSCAR PROFESSORES POR CONSELHO
    @GetMapping("{idConselho}/professores")
    public ResponseEntity<List<ProfessorResponse>> buscarProfessoresPorConselho(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarProfessoresPorConselho(id));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ConselhoProfessorResponse> update(@PathVariable Long id, @RequestBody @Valid ConselhoProfessorRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, request));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deletarConselhoProfessor(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}