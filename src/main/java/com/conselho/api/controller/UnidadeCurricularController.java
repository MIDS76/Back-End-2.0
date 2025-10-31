package com.conselho.api.controller;


import com.conselho.api.dto.request.UnidadeCurricularRequest;
import com.conselho.api.dto.response.UnidadeCurricularResponse;
import com.conselho.api.service.UnidadeCurricularService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/unidadeCurricular")
@AllArgsConstructor
public class UnidadeCurricularController {

    private final UnidadeCurricularService service;

    @PostMapping("/criar")
    public ResponseEntity<UnidadeCurricularResponse> postUnidadeCurricular(
            @RequestBody UnidadeCurricularRequest unidadeCurricularRequest
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criarUnidadeCurricular(unidadeCurricularRequest));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UnidadeCurricularResponse>> buscarTodasUnidades(
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarTodasUnidades());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<UnidadeCurricularResponse> buscarUnidadesPorId(
            @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarUnidadesPorId(id));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UnidadeCurricularResponse> atualizarUnidadeCurricular(
            @PathVariable Long id,
            @RequestBody UnidadeCurricularRequest unidadeCurricularRequest
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarUnidadeCurricular(id, unidadeCurricularRequest));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarUnidade(
            @PathVariable Long id
    ){
        service.deletarUnidadeCurricular(id);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadJson(
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