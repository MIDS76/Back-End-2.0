package com.conselho.api.controller.feedback;

import com.conselho.api.dto.request.feedback.ConselhoAlunoFeedbackRequestDTO;
import com.conselho.api.dto.response.feedback.ConselhoAlunoFeedbackResponseDTO;
import com.conselho.api.service.feedback.ConselhoAlunoFeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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

    @Operation(summary = "Cria um novo feedback para o aluno.", description = "Este endpoint cria um novo feedback de conselho para um aluno com base nas informações enviadas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Feedback criado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou insuficientes. Verifique os dados fornecidos."),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado. Entre em contato com o administrador caso isso seja um erro.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PostMapping("/criar")
    public ResponseEntity<ConselhoAlunoFeedbackResponseDTO> create (@RequestBody @Valid ConselhoAlunoFeedbackRequestDTO request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @Operation(summary = "Lista todos os feedbacks de conselho do aluno", description = "Este endpoint retorna uma lista contendo todos os feedbacks de aluno cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedbacks retornados com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado. Entre em contato com o administrador caso isso seja um erro.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nenhum feedback encontrado no sistema."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<ConselhoAlunoFeedbackResponseDTO>> buscarTodos(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarTodos());
    }

    @Operation(summary = "Busca um feedback a partir do ID.", description = "Este endpoint retorna um feedback baseado no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback encontrado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado. Entre em contato com o administrador caso isso seja um erro.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nenhum feedback encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ConselhoAlunoFeedbackResponseDTO> buscarPorId(@PathVariable Long id, @RequestBody @Valid ConselhoAlunoFeedbackRequestDTO request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarPorId(id, request));
    }

    @Operation(summary = "Atualiza um feedback a partir do ID.", description = "Este endpoint atualiza as informações de um feedback cadastrado no sistema, baseado no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou insuficientes. Verifique os dados fornecidos."),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado. Entre em contato com o administrador caso isso seja um erro.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nenhum feedback encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ConselhoAlunoFeedbackResponseDTO> update (@PathVariable Long id, @RequestBody @Valid ConselhoAlunoFeedbackRequestDTO request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.update(id, request));
    }


    @Operation(summary = "Deleta um feedback a partir do ID.", description = "Este endpoint deleta um feedback baseado no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Feedback deletado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado. Entre em contato com o administrador caso isso seja um erro.", content = @Content),
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