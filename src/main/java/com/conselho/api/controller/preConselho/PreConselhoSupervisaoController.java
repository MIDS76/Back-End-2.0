package com.conselho.api.controller.preConselho;

import com.conselho.api.dto.request.preConselho.PreConselhoSupervisaoRequestDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoSupervisaoResponseDTO;
import com.conselho.api.service.preConselho.PreConselhoSupervisaoService;
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
@RequestMapping("/api/preConselhoSupervisao")
@AllArgsConstructor
@Valid
public class PreConselhoSupervisaoController {

    private PreConselhoSupervisaoService service;

    @Operation(
            summary = "Cria um novo pré-conselho sobre os supervisores.",
            description = "Este endpoint cria um pré-conselho sobre os supervisores, com base nos dados fornecidos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pré-conselho criado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum supervisor encontrado para criar o pré-conselho."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PostMapping("/criar")
    public ResponseEntity<PreConselhoSupervisaoResponseDTO> criarPreConselhoSupervisao (
        @Valid @RequestBody PreConselhoSupervisaoRequestDTO requestDTO
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criarPreConselhoSupervisao(requestDTO));
    }

    @Operation(
            summary = "Lista todos os pré-conselhos sobre os supervisores.",
            description = "Este endpoint retorna todos os pré-conselhos sobre os supervisores cadastrados no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pré-conselhos encontrados com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum pré-conselho encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<PreConselhoSupervisaoResponseDTO>> listarPreConselhoSupervisao() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarTodos());
    }

    @Operation(
            summary = "Busca um pré-conselho pelo ID.",
            description = "Este endpoint retorna as informações de pré-conselho sobre os supervisores cadastrado no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pré-conselho encontrado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum pré-conselho encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/buscar/{id}")
    public ResponseEntity<PreConselhoSupervisaoResponseDTO> buscarPreConselhoSupervisaoPorId (
        @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarPorId(id));
    }

    @Operation(
            summary = "Atualiza um pré-conselho a partir do ID.",
            description = "Este endpoint atualiza as informações de um pré-conselho sobre os supervisores cadastrado no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pré-conselho atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição."),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum pré-conselho encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<PreConselhoSupervisaoResponseDTO> atualizarPreConselhoSupervisaoPorId (
        @PathVariable Long id,
        @Valid @RequestBody PreConselhoSupervisaoRequestDTO requestDTO
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarPreConselhoSupervisao(id,requestDTO));
    }

    @Operation(
            summary = "Deleta um pré-conselho a partir do ID.",
            description = "Este endpoint deleta as informações de um pré-conselho sobre os supervisores cadastrado no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pré-conselho deletado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum pré-conselho encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarPreConselhoSupervisao (
        @PathVariable Long id
    ){
        service.deletarPreConselhoSupervisao(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
