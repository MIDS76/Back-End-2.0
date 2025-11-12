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

    // FAZER

    @PostMapping("/criar")
    public ResponseEntity<TurmaResponseDTO> criarTurma(
          @Valid @RequestBody TurmaRequestDTO request
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criarTurma(request));
    }

    @Operation(summary = "Lista todas as turmas", description = "Retorna uma lista contendo todas as turmas cadastradas no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turmas listadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhuma turma encontrada")
    })
    @GetMapping("/listar")
    public ResponseEntity<List<TurmaResponseDTO>> listarTurmas(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarTurmas());
    }

    @Operation(summary = "Busca uma turma por ID", description = "Retorna os dados de uma turma específica com base no ID informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turma encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Turma não encontrada")
    })
    @GetMapping("/buscar/{id}")
    public ResponseEntity<TurmaResponseDTO> buscarTurmaPorId(
            @PathVariable Long idTurma
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarTurmaPorId(idTurma));
    }

    @Operation(summary = "Atualiza uma turma existente", description = "Atualiza as informações de uma turma com base no ID e nos novos dados fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turma atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos na requisição"),
            @ApiResponse(responseCode = "404", description = "Turma não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<TurmaResponseDTO> atualizarTurma(
            @PathVariable Long idTurma,
            @Valid @RequestBody TurmaRequestDTO request
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarTurma(idTurma,request));
    }

    @Operation(summary = "Deleta uma turma", description = "Remove permanentemente uma turma do sistema com base no ID informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turma deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Turma não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
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
