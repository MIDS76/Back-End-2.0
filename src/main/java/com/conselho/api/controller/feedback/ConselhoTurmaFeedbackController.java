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
public class ConselhoTurmaFeedbackController { // TURMA GERAL
    private ConselhoTurmaFeedbackService service;

    @Operation(summary = "Cria um novo feedback para a turma.", description = "Este endpoint cria um novo feedback para a turma em geral.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Feedback para a turma criado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou insuficientes fornecidos no corpo da requisição."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PostMapping("/criar")
    public ResponseEntity<ConselhoTurmaFeedbackResponseDTO> create (@RequestBody @Valid ConselhoTurmaFeedbackRequestDTO request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(request));
    }


    @Operation(summary = "Lista todos os feedbacks de todas as turmas.", description = "Este endpoint retorna uma lista contendo todos os feedbacks para todas as turmas cadastradas no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedbacks encontrados com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Nenhum feedback encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<ConselhoTurmaFeedbackResponseDTO>> buscarTodos(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarTodos());
    }

    @Operation(summary = "Busca um feedback a partir do ID.", description = "Este endpoint retorna um feedback baseado no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback encontrado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Nenhum feedback encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou insuficientes para a busca."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ConselhoTurmaFeedbackResponseDTO> buscarPorId(@PathVariable Long id, @RequestBody @Valid ConselhoTurmaFeedbackRequestDTO request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarPorId(id, request));
    }

    @Operation(summary = "Atualiza um feedback a partir do ID.", description = "Este endpoint atualiza um feedback baseado no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou insuficientes para atualização."),
            @ApiResponse(responseCode = "404", description = "Nenhum feedback encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ConselhoTurmaFeedbackResponseDTO> update (@PathVariable Long id, @RequestBody @Valid ConselhoTurmaFeedbackRequestDTO request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.update(id, request));
    }

    @Operation(summary = "Deleta um feedback a partir do ID.", description = "Este endpoint deleta um feedback baseado no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Feedback deletado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Nenhum feedback encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}