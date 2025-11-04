package com.conselho.api.controller;

import com.conselho.api.dto.request.PreConselhoRequest;
import com.conselho.api.dto.response.PreConselhoResponse;
import com.conselho.api.service.PreConselhoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Valid
@RestController
@RequestMapping("/api/preConselho")
public class PreConselhoController {

    private PreConselhoService service;

    @PostMapping("/criar")
    public ResponseEntity<PreConselhoResponse> create (@RequestBody @Valid PreConselhoRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criarPreConselhoAutomatico(request));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PreConselhoResponse>> buscarTodos(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarTodos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<PreConselhoResponse> buscarPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarPorId(id));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<PreConselhoResponse> update (@PathVariable Long id, @RequestBody @Valid PreConselhoRequest request){
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
