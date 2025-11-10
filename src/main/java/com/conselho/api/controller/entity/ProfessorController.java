package com.conselho.api.controller.entity;

import com.conselho.api.dto.request.entity.ProfessorRequestDTO;
import com.conselho.api.dto.response.entity.ProfessorResponseDTO;
import com.conselho.api.service.entity.ProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professores")
@AllArgsConstructor
@Tag(name = "Professores", description = "Endpoints para gerenciamento de professores")
public class ProfessorController {

    private final ProfessorService service;

    @Operation(summary = "Lista todos os professores", description = "Retorna uma lista contendo todos os professores cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professores encontrados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum professor encontrado")
    })
    @GetMapping("/listar")
    public ResponseEntity<List<ProfessorResponseDTO>> listarProfessores(
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarProfessores());
    }

    @Operation(summary = "Busca um professor por ID", description = "Retorna os dados de um professor específico a partir do seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ProfessorResponseDTO> buscarProfessorPorId(
            @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarProfessorPorId(id));
    }

    @Operation(summary = "Atualiza um professor existente", description = "Atualiza as informações de um professor com base no ID e nos novos dados fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos na requisição"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ProfessorResponseDTO> atualizarProfessor(
            @PathVariable Long id,
            @RequestBody ProfessorRequestDTO professorRequest
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarProfessor(id, professorRequest));
    }

    @Operation(summary = "Deleta um professor", description = "Remove permanentemente um professor do sistema com base no ID informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarProfessor(
            @PathVariable Long id
    ){
        service.deletarProfessor(id);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}