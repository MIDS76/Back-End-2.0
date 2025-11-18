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

    @Operation(
            summary = "Cria um novo feedback para a turma.",
            description = "Este endpoint cria um novo feedback para a turma, com base nos dados fornecidos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Feedback criado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhuma turma encontrada para criar o feedback."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PostMapping("/criar")
    public ResponseEntity<ConselhoTurmaFeedbackResponseDTO> create (@RequestBody @Valid ConselhoTurmaFeedbackRequestDTO request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @Operation(
            summary = "Lista todos os feedbacks.",
            description = "Este endpoint retorna todos os feedbacks cadastrados no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedbacks encontrados com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum feedback encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<ConselhoTurmaFeedbackResponseDTO>> buscarTodos(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarTodos());
    }

    @Operation(
            summary = "Busca um feedback pelo ID.",
            description = "Este endpoint retorna as informações de um feedback cadastrado no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback encontrado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum feedback encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ConselhoTurmaFeedbackResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarPorId(id));
    }

    @Operation(
            summary = "Atualiza um feedback a partir do ID.",
            description = "Este endpoint atualiza as informações de um feedback cadastrado no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição."),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum feedback encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ConselhoTurmaFeedbackResponseDTO> update (@PathVariable Long id, @RequestBody @Valid ConselhoTurmaFeedbackRequestDTO request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.update(id, request));
    }

    @Operation(
            summary = "Deleta um feedback a partir do ID.",
            description = "Este endpoint deleta as informações de um feedback cadastrado no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Feedback deletado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
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