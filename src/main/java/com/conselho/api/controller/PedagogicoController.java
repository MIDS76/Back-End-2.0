package com.conselho.api.controller;

import com.conselho.api.dto.request.PedagogicoRequest;
import com.conselho.api.dto.response.PedagogicoResponse;
import com.conselho.api.service.PedagogicoService;
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
@RequestMapping("/api/pedagogico")
public class PedagogicoController {
    private final PedagogicoService service;


    @Operation(summary = "Lista todos os pedagogicos", description = "Esse endpoint retorna todos os pedagogicos cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedagogicos encontrados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum pedagogico encontrado")
    })
    @GetMapping("/listar")
    public ResponseEntity<List<PedagogicoResponse>> buscarTodos (){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarTodos());
    }

    @Operation(summary = "Busca um pedagogico pelo ID", description = "Esse endpoint retorna um pedagogico específico a partir do ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedagogico encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedagogico não encontrado")
    })
    @GetMapping("/buscar/{id}")
    public ResponseEntity<PedagogicoResponse> buscarPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorId(id));
    }

    @Operation(summary = "Atualiza um pedagogico existente", description = "Esse endpoint atualiza as informações de um pedagogico existente no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedagogico atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados de entrada"),
            @ApiResponse(responseCode = "404", description = "Pedagogico não encontrado")
    })
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<PedagogicoResponse> update (@PathVariable Long id, @RequestBody @Valid PedagogicoRequest request){
        service.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Deleta um pedagogico", description = "Esse endpoint remove um pedagogico do sistema pelo ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedagogico deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedagogico não encontrado")
    })
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        service.deletarPedagogico(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}