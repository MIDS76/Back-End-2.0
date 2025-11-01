package com.conselho.api.controller;

import com.conselho.api.dto.request.ConselhoRequest;
import com.conselho.api.dto.response.ConselhoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import com.conselho.api.service.ConselhoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "Conselhos", description = "Endpoints para gerenciamento de conselhos")
@RequestMapping("/api/conselhos")
public class ConselhoController {
    private ConselhoService service;

    @Operation(
            summary = "Criar novo conselho",
            description = "Cria um novo conselho com base nas informações enviadas."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Conselho criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConselhoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro de validação no corpo da requisição", content = @Content),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado", content = @Content)
    })
    @PostMapping("/criar")
    public ResponseEntity<ConselhoResponse> create(@RequestBody @Valid ConselhoRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarConselho(request));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ConselhoResponse>> buscarTodos(){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarTodos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ConselhoResponse> buscarPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPoriD(id));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ConselhoResponse> update(@PathVariable Long id, @RequestBody @Valid ConselhoRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, request));
    }

    @DeleteMapping("deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deletarConselho(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
