package com.conselho.api.controller.entity;

import com.conselho.api.dto.request.entity.WegRequestDTO;
import com.conselho.api.dto.response.entity.WegResponseDTO;
import com.conselho.api.service.entity.WegService;
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
@RequestMapping("/api/weg")
public class WegController {
    private WegService service;

    // Listar
    @Operation(summary = "Lista todos os colaboradores WEG.", description = "Este endpoint retorna uma lista contendo todos os colaboradores cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Colaboradores encontrados com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Nenhum colaborador encontrado.")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<WegResponseDTO>> buscarTodos(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarTodos());
    }

    // Buscar
    @Operation(summary = "Busca um colaborador pelo ID.", description = "Este endpoint retorna um colaborador baseado no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Colaborador encontrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Nenhum colaborador encontrado a partir do ID fornecido.")
    })

    @GetMapping("/buscar/{id}")
    public ResponseEntity<WegResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarPorId(id));
    }

    // Atualizar
    @Operation(summary = "Atualiza um colaborador pelo ID.", description = "Este endpoint atualiza um colaborador baseado no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Colaborador atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou insuficientes."),
            @ApiResponse(responseCode = "404", description = "Nenhum colaborador encontrado a partir do ID fornecido.")
    })

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<WegResponseDTO> update (@PathVariable Long id, @RequestBody @Valid WegRequestDTO request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.update(id, request));
    }

    // Deletar
    @Operation(summary = "Deleta um colaborar pelo ID.", description = "Este endpoint deleta um colaborador baseado no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Colaborador deletado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Nenhum colaborador encontrado a partir do ID fornecido.")
    })

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
