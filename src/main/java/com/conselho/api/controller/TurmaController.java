package com.conselho.api.controller;

import com.conselho.api.dto.request.TurmaRequestDTO;
import com.conselho.api.dto.response.TurmaResponseDTO;
import com.conselho.api.service.TurmaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/api/turmas")
@RestController
@AllArgsConstructor
@Tag(name = "Turmas", description = "Endpoints para gerenciamento de turmas")
public class TurmaController {

    private TurmaService service;

    @Operation(
            summary = "Cria uma nova turma.",
            description = "Este endpoint cria uma nova turma, com base nos dados fornecidos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Turma criada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PostMapping("/criar")
    public ResponseEntity<TurmaResponseDTO> criarTurma(
          @Valid @RequestBody TurmaRequestDTO request
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criarTurma(request));
    }

    @Operation(
            summary = "Lista todos as turmas.",
            description = "Este endpoint retorna todas as turmas cadastrados no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turmas encontradas com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhuma turma encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<TurmaResponseDTO>> listarTurmas(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarTurmas());
    }

    @Operation(
            summary = "Busca uma turma pelo ID.",
            description = "Este endpoint retorna as informações de uma turma cadastrada no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turma encontrada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhuma turma encontrada com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/buscar/{id}")
    public ResponseEntity<TurmaResponseDTO> buscarTurmaPorId(
            @PathVariable Long idTurma
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarTurmaPorId(idTurma));
    }

    @Operation(
            summary = "Atualiza uma turma a partir do ID.",
            description = "Este endpoint atualiza as informações de uma turma cadastrada no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turma atualizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição."),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhuma encontrada com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<TurmaResponseDTO> atualizarTurma(
            @PathVariable Long idTurma,
            @Valid @RequestBody TurmaRequestDTO request
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarTurma(idTurma,request));
    }

    @Operation(
            summary = "Deleta uma turma a partir do ID.",
            description = "Este endpoint deleta as informações de uma turma cadastrada no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Turma deletada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhuma turma encontrada com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarTurma(
            @PathVariable Long idTurma
    ){
        service.deletarTurma(idTurma);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
