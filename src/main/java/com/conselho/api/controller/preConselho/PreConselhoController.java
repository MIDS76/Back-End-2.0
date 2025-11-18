package com.conselho.api.controller.preConselho;

import com.conselho.api.dto.request.preConselho.PreConselhoRequestDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoResponseDTO;
import com.conselho.api.service.preConselho.PreConselhoService;
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
    public ResponseEntity<PreConselhoResponseDTO> create (@RequestBody @Valid PreConselhoRequestDTO request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criarPreConselhoAutomatico(request));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PreConselhoResponseDTO>> buscarTodos(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarTodos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<PreConselhoResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarPorId(id));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<PreConselhoResponseDTO> update (@PathVariable Long id, @RequestBody @Valid PreConselhoRequestDTO request){
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
