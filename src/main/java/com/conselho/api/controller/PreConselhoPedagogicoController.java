package com.conselho.api.controller;

import com.conselho.api.dto.request.PreConselhoPedagogicoRequestDTO;
import com.conselho.api.dto.response.PreConselhoPedagogicoResponseDTO;
import com.conselho.api.service.PreConselhoPedagogicoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/api/preConselhoPedagogico")
@RestController
@AllArgsConstructor
@Valid
public class PreConselhoPedagogicoController {
    private PreConselhoPedagogicoService service;

    @PostMapping("/criar")
    public ResponseEntity<PreConselhoPedagogicoResponseDTO> criarPreConselhoPedagogico (
            @Valid @RequestBody PreConselhoPedagogicoRequestDTO requestDTO
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criarPreConselhoPedagogico(requestDTO));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PreConselhoPedagogicoResponseDTO>> listarPreConselhoPedagogico() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarTodos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<PreConselhoPedagogicoResponseDTO> buscarPreConselhoPedagogicoPorId(
            @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarPorId(id));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<PreConselhoPedagogicoResponseDTO> atualizarPreConselhoPedagogicoPorId(
            @Valid @RequestBody PreConselhoPedagogicoRequestDTO requestDTO,
            @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarPreConselhoPedagogico(id,requestDTO));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<PreConselhoPedagogicoResponseDTO> deletarPreConselhoPedagogicoPorId(
            @PathVariable Long id
    ){
        service.deletarPreConselhoPedagogico(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
