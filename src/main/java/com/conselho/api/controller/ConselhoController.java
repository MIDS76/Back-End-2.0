package com.conselho.api.controller;

import com.conselho.api.dto.request.ConselhoRequest;
import com.conselho.api.dto.response.ConselhoResponse;
import com.conselho.api.model.conselho.Conselho;
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
    public ResponseEntity<ConselhoResponse> create(@RequestBody @Valid ConselhoRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarConselho(request));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ConselhoResponse>> buscarTodos(){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarTodos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ConselhoResponse> buscarPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPoriD(id));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ConselhoResponse> update(@PathVariable Long id, @RequestBody @Valid ConselhoRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, request));
    }

    @DeleteMapping("deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deletarConselho(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
