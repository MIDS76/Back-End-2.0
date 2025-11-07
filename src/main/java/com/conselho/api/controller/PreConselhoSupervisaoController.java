package com.conselho.api.controller;

import com.conselho.api.dto.request.PreConselhoSupervisaoRequestDTO;
import com.conselho.api.dto.response.PreConselhoSupervisaoResponseDTO;
import com.conselho.api.service.PreConselhoSupervisaoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/preConselhoSupervisao")
@AllArgsConstructor
@Valid
public class PreConselhoSupervisaoController {

    private PreConselhoSupervisaoService service;

    @PostMapping("/criar")
    public ResponseEntity<PreConselhoSupervisaoResponseDTO> criarPreConselhoSupervisao (
        @Valid @RequestBody PreConselhoSupervisaoRequestDTO requestDTO
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criarPreConselhoSupervisao(requestDTO));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PreConselhoSupervisaoResponseDTO>> listarPreConselhoSupervisao() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarTodos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<PreConselhoSupervisaoResponseDTO> buscarPreConselhoSupervisaoPorId (
        @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarPorId(id));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<PreConselhoSupervisaoResponseDTO> atualizarPreConselhoSupervisaoPorId (
        @PathVariable Long id,
        @Valid @RequestBody PreConselhoSupervisaoRequestDTO requestDTO
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarPreConselhoSupervisao(id,requestDTO));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarPreConselhoSupervisao (
        @PathVariable Long id
    ){
        service.deletarPreConselhoSupervisao(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
