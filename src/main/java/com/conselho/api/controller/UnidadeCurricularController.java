package com.conselho.api.controller;


import com.conselho.api.dto.request.UnidadeCurricularRequestDTO;
import com.conselho.api.dto.response.UnidadeCurricularResponseDTO;
import com.conselho.api.service.UnidadeCurricularService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/unidadeCurricular")
@AllArgsConstructor
public class UnidadeCurricularController {

    private final UnidadeCurricularService service;

    @PostMapping("/criar")
    public ResponseEntity<UnidadeCurricularResponseDTO> criarUnidadeCurricular(
            @RequestBody UnidadeCurricularRequestDTO unidadeCurricularRequestDTO
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criarUnidadeCurricular(unidadeCurricularRequestDTO));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UnidadeCurricularResponseDTO>> listarUnidadesCurriculares(
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarUnidadesCurriculares());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<UnidadeCurricularResponseDTO> buscarUnidadesPorId(
            @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarUnidadesPorId(id));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UnidadeCurricularResponseDTO> atualizarUnidadeCurricular(
            @PathVariable Long id,
            @RequestBody UnidadeCurricularRequestDTO unidadeCurricularRequestDTO
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarUnidadeCurricular(id, unidadeCurricularRequestDTO));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarUnidadeCurricular(
            @PathVariable Long id
    ){
        service.deletarUnidadeCurricular(id);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/upload")
    public ResponseEntity<String> processarJson(
            @RequestParam("file")
            MultipartFile file)
    {
        try {
            service.processarJson(file);
            return  ResponseEntity.status(HttpStatus.OK)
                    .body("Arquivo JSON processado com sucesso!");
        } catch (IOException e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao processar o arquivo JSON.");

        }
    }
}