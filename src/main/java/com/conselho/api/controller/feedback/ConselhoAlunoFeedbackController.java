package com.conselho.api.controller.feedback;

import com.conselho.api.dto.request.feedback.ConselhoAlunoFeedbackRequestDTO;
import com.conselho.api.dto.response.feedback.ConselhoAlunoFeedbackResponseDTO;
import com.conselho.api.service.feedback.ConselhoAlunoFeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    // Criar
    @Operation(summary = "Cria um novo feedback para o aluno.", description = "Este endpoint cria um novo feedback de conselho para um aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback criado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Dados inválidos ou insuficientes.")
    })

    @PostMapping("/criar")
    public ResponseEntity<ConselhoAlunoFeedbackResponseDTO> create (@RequestBody @Valid ConselhoAlunoFeedbackRequestDTO request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    // Listar
    @Operation(summary = "Lista todos os feedbacks de conselho do aluno", description = "Este endpoint retorna uma lista contendo todos os feedbacks de aluno cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback retornado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Nenhum feedback encontrado.")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<ConselhoAlunoFeedbackResponseDTO>> buscarTodos(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarTodos());
    }

    // Buscar feedback por ID
    @Operation(summary = "Busca um feedback pelo ID.", description = "Este endpoint retorna um feedback baseado no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback encontrado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Nenhum feedback encontrado a partir do ID fornecido.")
    })

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ConselhoAlunoFeedbackResponseDTO> buscarPorId(@PathVariable Long id, @RequestBody @Valid ConselhoAlunoFeedbackRequestDTO request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarPorId(id, request));
    }

    // Atualizar através do ID
    @Operation(summary = "Atualiza um feedback pelo ID.", description = "Este endpoint atualiza um feedback baseado no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou insuficientes."),
            @ApiResponse(responseCode = "404", description = "Nenhum feedback encontrado a partir do ID fornecido.")
    })

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ConselhoAlunoFeedbackResponseDTO> update (@PathVariable Long id, @RequestBody @Valid ConselhoAlunoFeedbackRequestDTO request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.update(id, request));
    }

    // Deletar através do ID
    @Operation(summary = "Deleta um feedback pelo ID.", description = "Este endpoint deleta um feedback baseado no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback deletado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Nenhum feedback encontrado a partir do ID fornecido.")
    })

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}