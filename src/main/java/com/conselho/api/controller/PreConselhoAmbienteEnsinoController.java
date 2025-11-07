package com.conselho.api.controller;

import com.conselho.api.dto.request.PreConselhoAmbienteEnsinoRequestDTO;
import com.conselho.api.dto.response.PreConselhoAmbienteEnsinoResponseDTO;
import com.conselho.api.service.PreConselhoAmbienteEnsinoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/preConselhoAmbienteEnsino")
@RestController
@AllArgsConstructor
@Valid
public class PreConselhoAmbienteEnsinoController {
    private PreConselhoAmbienteEnsinoService service;

    @PostMapping("/criar")
    public ResponseEntity<PreConselhoAmbienteEnsinoResponseDTO> criarPreConselhoAmbienteEnsino (
            @Valid @RequestBody PreConselhoAmbienteEnsinoRequestDTO requestDTO
            ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criarPreConselhoAmbienteEnsino(requestDTO));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PreConselhoAmbienteEnsinoResponseDTO>> listarPreConselhoAmbienteEnsino() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarTodos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<PreConselhoAmbienteEnsinoResponseDTO> buscarPreConselhoAmbienteEnsinoPorId(
            @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarPorId(id));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<PreConselhoAmbienteEnsinoResponseDTO> atualizarPreConselhoAmbienteEnsinoPorId(
        @Valid @RequestBody PreConselhoAmbienteEnsinoRequestDTO requestDTO,
        @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarPreConselhoAmbienteEnsino(id,requestDTO));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<PreConselhoAmbienteEnsinoResponseDTO> deletarPreConselhoAmbientePorId(
        @PathVariable Long id
    ){
        service.deletarPreConselhoAmbienteEnsino(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
