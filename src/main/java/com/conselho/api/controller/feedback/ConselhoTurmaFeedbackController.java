package com.conselho.api.controller.feedback;

import com.conselho.api.dto.request.feedback.ConselhoTurmaFeedbackRequestDTO;
import com.conselho.api.dto.response.feedback.ConselhoTurmaFeedbackResponseDTO;
import com.conselho.api.serviceTestes.feedback.ConselhoTurmaFeedbackService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/conselhoTurmasFeedback")
public class ConselhoTurmaFeedbackController {
    private ConselhoTurmaFeedbackService service;

    @PostMapping("/criar")
    public ResponseEntity<ConselhoTurmaFeedbackResponseDTO> create (@RequestBody @Valid ConselhoTurmaFeedbackRequestDTO request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ConselhoTurmaFeedbackResponseDTO>> buscarTodos(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarTodos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ConselhoTurmaFeedbackResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarPorId(id));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ConselhoTurmaFeedbackResponseDTO> update (@PathVariable Long id, @RequestBody @Valid ConselhoTurmaFeedbackRequestDTO request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.update(id, request));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}