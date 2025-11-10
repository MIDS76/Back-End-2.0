package com.conselho.api.controller;

import com.conselho.api.dto.request.AtualizarEtapaRequestDTO;
import com.conselho.api.dto.request.ConselhoRequestDTO;
import com.conselho.api.dto.response.ConselhoResponseDTO;
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

    @Operation(summary = "Criar novo conselho", description = "Cria um novo conselho com base nas informações enviadas."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Conselho criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConselhoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro de validação no corpo da requisição", content = @Content),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado", content = @Content)
    })
    @PostMapping("/criar")
    public ResponseEntity<ConselhoResponseDTO> create(@RequestBody @Valid ConselhoRequestDTO request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarConselho(request));
    }

    @Operation(summary = "Lista todos os conselhos", description = "Esse endpoint retorna todos os conselhos cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conselhos encontrados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum conselho encontrado")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<ConselhoResponseDTO>> listarConselhos(){
        return ResponseEntity.status(HttpStatus.OK).body(service.listarConselhos());
    }

    @Operation(summary = "Busca um conselho pelo ID", description = "Esse endpoint retorna um conselho específico a partir do ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conselho encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conselho não encontrado")
    })

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ConselhoResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarConselhoPorId(id));
    }

    @Operation(summary = "Atualiza um conselho existente", description = "Esse endpoint atualiza as informações de um conselho existente no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conselho atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados de entrada"),
            @ApiResponse(responseCode = "404", description = "Conselho não encontrado")
    })

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ConselhoResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ConselhoRequestDTO request){
        return ResponseEntity.status(HttpStatus.OK).body(service.atualizarConselho(id, request));
    }

    // QUANDO PRECISAR MUDAR ETAPA
    @PatchMapping("/atualizar/{id}/etapa")
    public ResponseEntity<ConselhoResponseDTO> updateEtapa(@PathVariable Long id, @RequestBody @Valid AtualizarEtapaRequestDTO request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarEtapa(
                        id,
                        request.novaEtapa(),
                        request.dataInicioPre(),
                        request.dataFimPre()));
    }

    @Operation(summary = "Deleta um conselho", description = "Esse endpoint remove um conselho do sistema pelo ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conselho deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conselho não encontrado")
    })

    @DeleteMapping("deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deletarConselho(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
