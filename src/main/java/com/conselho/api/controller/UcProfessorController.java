package com.conselho.api.controller;

import com.conselho.api.dto.request.UcProfessorRequestDTO;
import com.conselho.api.dto.response.UcProfessorResponseDTO;
import com.conselho.api.service.UcProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ucprofessor")
@AllArgsConstructor
public class UcProfessorController {

    private UcProfessorService ucProfessorService;

    @Operation(
            summary = "Cria uma nova UC para um professor.",
            description = "Este endpoint cria uma nova UC para o professor, associando-a a um conselho e unidade curricular."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "UC Professor criada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos na requisição."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Tente novamente mais tarde.")
    })

    @PostMapping("/criar")
    public ResponseEntity<UcProfessorResponseDTO> criarUcProfessor(
            @Valid @RequestBody UcProfessorRequestDTO requestDTO
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ucProfessorService.criarUcProfessor(requestDTO));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UcProfessorResponseDTO>> listarTodos() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ucProfessorService.listarUcProfessor());
    }

    @Operation(
            summary = "Busca uma UC para um professor pelo ID.",
            description = "Este endpoint retorna as informações da UC de um professor, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UC Professor encontrado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "UC Professor não encontrada com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Tente novamente mais tarde.")
    })

    @GetMapping("/buscar/{id}")
    public ResponseEntity<UcProfessorResponseDTO> buscarUcProfessorPorId(
            @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ucProfessorService.buscarUcProfessorPorId(id));
    }

    @Operation(
            summary = "Atualiza uma UC para um professor.",
            description = "Este endpoint atualiza a associação de uma UC para um professor, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UC Professor atualizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos na requisição."),
            @ApiResponse(responseCode = "404", description = "UC Professor não encontrada com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Tente novamente mais tarde.")
    })

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UcProfessorResponseDTO> atualizarUcProfessor(
            @PathVariable Long id,
            @Valid @RequestBody UcProfessorRequestDTO requestDTO
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ucProfessorService.atualizarUcProfessor(requestDTO,id));
    }

    @Operation(
            summary = "Deleta uma UC de professor pelo ID.",
            description = "Este endpoint deleta a UC de um professor, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "UC Professor deletada com sucesso!"),
            @ApiResponse(responseCode = "404", description = "UC Professor não encontrada com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Tente novamente mais tarde.")
    })

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarUcProfessor(
            @PathVariable Long id
    ){
        ucProfessorService.deletarUcProfessor(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
