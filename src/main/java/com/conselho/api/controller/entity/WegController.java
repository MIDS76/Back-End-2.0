package com.conselho.api.controller.entity;

import com.conselho.api.dto.request.entity.WegRequestDTO;
import com.conselho.api.dto.response.entity.WegResponseDTO;
import com.conselho.api.service.entity.WegService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/weg")
public class WegController {
    private WegService service;

    @GetMapping("/listar")
    public ResponseEntity<List<WegResponseDTO>> buscarTodos(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarTodos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<WegResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarPorId(id));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<WegResponseDTO> update (@PathVariable Long id, @RequestBody @Valid WegRequestDTO request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.update(id, request));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
