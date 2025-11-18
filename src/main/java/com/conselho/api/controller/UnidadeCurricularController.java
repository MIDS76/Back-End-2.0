package com.conselho.api.controller;


import com.conselho.api.dto.request.UnidadeCurricularRequestDTO;
import com.conselho.api.dto.response.UnidadeCurricularResponseDTO;
import com.conselho.api.service.UnidadeCurricularService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @PostMapping("/criarLista")
    public ResponseEntity<Void> listarUnidadesCurriculares(@RequestBody ArrayList<UnidadeCurricularRequestDTO> ucRequest) {
        for(UnidadeCurricularRequestDTO requestDTO : ucRequest){
            service.criarUnidadeCurricular(requestDTO);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UnidadeCurricularResponseDTO>> listarUnidadesCurriculares(){
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
}