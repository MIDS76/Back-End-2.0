package com.conselho.api.controller;

import com.conselho.api.dto.request.ConselhoRequestDTO;
import com.conselho.api.dto.response.ConselhoResponseDTO;
import com.conselho.api.service.ConselhoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/conselhos")
public class ConselhoController {
    private ConselhoService service;

    @PostMapping("/criar")
    public ResponseEntity<ConselhoResponseDTO> create(@RequestBody @Valid ConselhoRequestDTO request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarConselho(request));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ConselhoResponseDTO>> listarConselhos(){
        return ResponseEntity.status(HttpStatus.OK).body(service.listarConselhos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ConselhoResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarConselhoPorId(id));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ConselhoResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ConselhoRequestDTO request){
        return ResponseEntity.status(HttpStatus.OK).body(service.atualizarConselho(id, request));
    }

    @DeleteMapping("deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deletarConselho(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
