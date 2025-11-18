package com.conselho.api.controller.entity;

import com.conselho.api.dto.request.SupervisorRequestDTO;
import com.conselho.api.dto.response.entity.SupervisorResponseDTO;
import com.conselho.api.service.entity.SupervisorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supervisor")
@AllArgsConstructor
public class SupervisorController {

    private final SupervisorService service;

    @Operation(
            summary = "Lista todos os supervisores.",
            description = "Este endpoint retorna todos os supervisores cadastrados no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supervisores encontrados com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum supervisor encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<SupervisorResponseDTO>> buscarTodosSupervisor(
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarSupervisores());
    }

    @Operation(
            summary = "Busca um supervisor pelo ID.",
            description = "Este endpoint retorna as informações de um supervisor cadastrado no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supervisor encontrado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum supervisor encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/buscar/{id}")
    public ResponseEntity<SupervisorResponseDTO> buscarSupervisorPorId(
            @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarSupervisorPorId(id));
    }

    @Operation(
            summary = "Atualiza um supervisor a partir do ID.",
            description = "Este endpoint atualiza as informações de um supervisor cadastrado no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supervisor atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição."),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum supervisor encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<SupervisorResponseDTO> atualizarSupervisor(
            @PathVariable Long id,
            @RequestBody SupervisorRequestDTO supervisorRequestDTO
    ){

        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarSupervisor(id, supervisorRequestDTO));
    }

    @Operation(
            summary = "Deleta um supervisor a partir do ID.",
            description = "Este endpoint deleta as informações de um supervisor cadastrado no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Supervisor deletado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum supervisor encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
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
