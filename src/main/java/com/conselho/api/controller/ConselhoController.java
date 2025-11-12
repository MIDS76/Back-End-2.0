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

    @Operation(summary = "Cria um novo conselho.", description = "Cria um novo conselho com base nas informações enviadas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Conselho criado com sucesso!",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConselhoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado. Entre em contato com o administrador caso isso seja um erro.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PostMapping("/criar")
    public ResponseEntity<ConselhoResponseDTO> create(@RequestBody @Valid ConselhoRequestDTO request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarConselho(request));
    }


    @Operation(summary = "Lista todos os conselhos.", description = "Este endpoint retorna todos os conselhos cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conselhos encontrados com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Nenhum conselho encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<ConselhoResponseDTO>> listarConselhos(){
        return ResponseEntity.status(HttpStatus.OK).body(service.listarConselhos());
    }

    @Operation(summary = "Busca um conselho a partir do ID.", description = "Este endpoint retorna um conselho cadastrado no sistema, baseado no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conselho encontrado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Nenhum conselho encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ConselhoResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarConselhoPorId(id));
    }


    @Operation(summary = "Atualiza um conselho existente.", description = "Este endpoint atualiza as informações de um conselho cadastrado no sistema, baseado no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conselho atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição."),
            @ApiResponse(responseCode = "404", description = "Nenhum conselho encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ConselhoResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ConselhoRequestDTO request){
        return ResponseEntity.status(HttpStatus.OK).body(service.atualizarConselho(id, request));
    }

    // QUANDO PRECISAR MUDAR ETAPA
    @Operation(summary = "Atualiza a etapa de um processo.", description = "Este endpoint permite a atualização da etapa de um processo, incluindo o nome da nova etapa e as datas previstas de início e fim.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Etapa atualizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição."),
            @ApiResponse(responseCode = "404", description = "Nenhum processo encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })
    @PatchMapping("/atualizar/{id}/etapa")
    public ResponseEntity<ConselhoResponseDTO> updateEtapa(@PathVariable Long id, @RequestBody @Valid AtualizarEtapaRequestDTO request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarEtapa(
                        id,
                        request.novaEtapa(),
                        request.dataInicioPre(),
                        request.dataFimPre()));
    }

    @Operation(summary = "Deleta um conselho a partir do ID.", description = "Este endpoint deleta um conselho baseado no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conselho deletado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Nenhum conselho encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @DeleteMapping("deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deletarConselho(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
