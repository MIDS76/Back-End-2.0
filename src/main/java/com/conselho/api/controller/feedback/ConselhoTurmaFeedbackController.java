package com.conselho.api.controller.feedback;

import com.conselho.api.dto.request.feedback.ConselhoTurmaFeedbackRequestDTO;
import com.conselho.api.dto.response.feedback.ConselhoTurmaFeedbackResponseDTO;
import com.conselho.api.service.feedback.ConselhoTurmaFeedbackService;
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
@RequestMapping("/api/conselhoTurmasFeedback")
public class ConselhoTurmaFeedbackController {
    private ConselhoTurmaFeedbackService service;

    // Criar
    @Operation(summary = "Cria um novo pré-conselho para a turma.", description = "Este endpoint cria um novo pré-conselho para o para a turma")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pré-conselho criado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Dados inválidos ou insuficientes.")
    })

    @PostMapping("/criar")
    public ResponseEntity<ConselhoTurmaFeedbackResponseDTO> create (@RequestBody @Valid ConselhoTurmaFeedbackRequestDTO request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    // Listar
    @Operation(summary = "Lista todos os pré-conselhos.", description = "Este endpoint retorna uma lista contendo todos os pré-conselhos cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pré-conselhos encontrados com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Nenhum pré-conselho encontrado.")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<ConselhoTurmaFeedbackResponseDTO>> buscarTodos(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarTodos());
    }

    // Buscar por ID
    @Operation(summary = "Busca um pré-conselho pelo ID.", description = "Este endpoint retorna um pré-conselho baseado no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pré-conselho encontrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Nenhum pré-conselho encontrado a partir do ID fornecido.")
    })

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ConselhoTurmaFeedbackResponseDTO> buscarPorId(@PathVariable Long id, @RequestBody @Valid ConselhoTurmaFeedbackRequestDTO request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarPorId(id, request));
    }

    // Atualizar por ID
    @Operation(summary = "Atualiza um pré-conselho pelo ID.", description = "Este endpoint atualiza um pré-conselho baseado no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pré-conselho atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou insuficientes."),
            @ApiResponse(responseCode = "404", description = "Nenhum pré-conselho encontrado a partir do ID fornecido.")
    })

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ConselhoTurmaFeedbackResponseDTO> update (@PathVariable Long id, @RequestBody @Valid ConselhoTurmaFeedbackRequestDTO request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.update(id, request));
    }

    // Deletar por ID
    @Operation(summary = "Deleta um pré-conselho pelo ID.", description = "Este endpoint deleta um pré-conselho baseado no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pré-conselho deletado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Nenhum pré-conselho encontrado a partir do ID fornecido.")
    })

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}