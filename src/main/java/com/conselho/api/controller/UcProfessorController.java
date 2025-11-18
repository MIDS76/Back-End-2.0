package com.conselho.api.controller;

import com.conselho.api.dto.request.UcProfessorRequestDTO;
import com.conselho.api.dto.response.UcProfessorResponseDTO;
import com.conselho.api.serviceTestes.UcProfessorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ucprofessor")
@AllArgsConstructor
public class UcProfessorController {

    private UcProfessorService ucProfessorService;

    @PostMapping("/criar")
    public ResponseEntity<UcProfessorResponseDTO> criarUcProfessor(
            @Valid @RequestBody UcProfessorRequestDTO requestDTO
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ucProfessorService.criarUcProfessor(requestDTO));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UcProfessorResponseDTO>> listarTodos() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ucProfessorService.listarUcProfessor());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<UcProfessorResponseDTO> buscarUcProfessorPorId(
            @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ucProfessorService.buscarUcProfessorPorId(id));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UcProfessorResponseDTO> atualizarUcProfessor(
            @PathVariable Long id,
            @Valid @RequestBody UcProfessorRequestDTO requestDTO
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ucProfessorService.atualizarUcProfessor(requestDTO,id));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarUcProfessor(
            @PathVariable Long id
    ){
        ucProfessorService.deletarUcProfessor(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
