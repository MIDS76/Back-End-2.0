package com.conselho.api.controller.entity;

import com.conselho.api.dto.request.SupervisorRequestDTO;
import com.conselho.api.dto.response.entity.SupervisorResponseDTO;
import com.conselho.api.service.entity.SupervisorService;
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
@RequestMapping("/api/supervisor")
@AllArgsConstructor
@Tag(name = "Supervisor", description = "Endpoints para gerenciamento de supervisores")
public class SupervisorController {

    private final SupervisorService service;

    @Operation(summary = "Lista todos os supervisores", description = "Retorna uma lista contendo todos os supervisores cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supervisores encontrados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum supervisor encontrado")
    })
    @GetMapping("/listar")
    public ResponseEntity<List<SupervisorResponseDTO>> buscarTodosSupervisor(
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarSupervisores());
    }

    @Operation(summary = "Busca um supervisor por ID", description = "Retorna os dados de um supervisor específico com base no ID informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supervisor encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Supervisor não encontrado")
    })
    @GetMapping("/buscar/{id}")
    public ResponseEntity<SupervisorResponseDTO> buscarSupervisorPorId(
            @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarSupervisorPorId(id));
    }

    @Operation(summary = "Atualiza um supervisor existente", description = "Atualiza as informações de um supervisor com base no ID e nos dados fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supervisor atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos na requisição"),
            @ApiResponse(responseCode = "404", description = "Supervisor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<SupervisorResponseDTO> atualizarSupervisor(
            @PathVariable Long id,
            @RequestBody SupervisorRequestDTO supervisorRequestDTO
    ){

        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarSupervisor(id, supervisorRequestDTO));
    }

    @Operation(summary = "Deleta um supervisor", description = "Remove permanentemente um supervisor do sistema com base no ID informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supervisor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Supervisor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarSupervisor(
            @PathVariable Long id
    ){
        service.deletarSupervisor(id);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
