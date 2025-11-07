package com.conselho.api.controller.feedback;

import com.conselho.api.dto.request.feedback.ConselhoAlunoFeedbackRequestDTO;
import com.conselho.api.dto.response.feedback.ConselhoAlunoFeedbackResponseDTO;
import com.conselho.api.service.feedback.ConselhoAlunoFeedbackService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/conselhoAlunosFeedbacks")
public class ConselhoAlunoFeedbackController {
    private ConselhoAlunoFeedbackService service;

    @PostMapping("/criar")
    public ResponseEntity<ConselhoAlunoFeedbackResponseDTO> create (@RequestBody @Valid ConselhoAlunoFeedbackRequestDTO request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ConselhoAlunoFeedbackResponseDTO>> buscarTodos(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarTodos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ConselhoAlunoFeedbackResponseDTO> buscarPorId(@PathVariable Long id, @RequestBody @Valid ConselhoAlunoFeedbackRequestDTO request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarPorId(id, request));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ConselhoAlunoFeedbackResponseDTO> update (@PathVariable Long id, @RequestBody @Valid ConselhoAlunoFeedbackRequestDTO request){
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