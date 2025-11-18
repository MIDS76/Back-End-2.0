package com.conselho.api.controller.preConselho;


import com.conselho.api.dto.request.preConselho.PreConselhoProfessorRequestDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoProfessorResponseDTO;
import com.conselho.api.serviceTestes.preConselho.PreConselhoProfessorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/preConselhoProfessor")
public class PreConselhoProfessorController {

    private PreConselhoProfessorService service;

    @PostMapping("/criar")
    public ResponseEntity<PreConselhoProfessorResponseDTO> criarPreConselhoProfessor(
            @Valid @RequestBody PreConselhoProfessorRequestDTO requestDTO
            ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criarPreConselhoProfessor(requestDTO));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PreConselhoProfessorResponseDTO>> listarPreConselhoProessor(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarPreConselhoProfessor());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<PreConselhoProfessorResponseDTO> buscarPreConselhoProfessorPorId(
        @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarPreConselhoProfessorPorId(id));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<PreConselhoProfessorResponseDTO> atualizarPreConselhoProfessorPorId(
        @PathVariable Long id,
        @Valid @RequestBody PreConselhoProfessorRequestDTO requestDTO
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarPreConselhoProfessor(id,requestDTO));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<PreConselhoProfessorResponseDTO> deletarPreConselhoProfessorPorId(
        @PathVariable Long id
    ){
        service.deletarPreConselhoProfessor(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

}
