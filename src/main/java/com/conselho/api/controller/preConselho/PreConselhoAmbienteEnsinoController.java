package com.conselho.api.controller.preConselho;

import com.conselho.api.dto.request.preConselho.PreConselhoAmbienteEnsinoRequestDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoAmbienteEnsinoResponseDTO;
import com.conselho.api.service.preConselho.PreConselhoAmbienteEnsinoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/preConselhoAmbienteEnsino")
@RestController
@AllArgsConstructor
@Valid
public class PreConselhoAmbienteEnsinoController {

    private PreConselhoAmbienteEnsinoService service;

    @Operation(
            summary = "Cria um novo pré-conselho sobre o ambiente de ensino.",
            description = "Este endpoint cria um pré-conselho sobre o ambiente de ensino, com base nos dados fornecidos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pré-conselho criado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum ambiente de ensino encontrado para criar o pré-conselho."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PostMapping("/criar")
    public ResponseEntity<PreConselhoAmbienteEnsinoResponseDTO> criarPreConselhoAmbienteEnsino (
            @Valid @RequestBody PreConselhoAmbienteEnsinoRequestDTO requestDTO
            ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criarPreConselhoAmbienteEnsino(requestDTO));
    }

    @Operation(
            summary = "Lista todos os pré-conselhos sobre o ambiente de ensino.",
            description = "Este endpoint retorna todos os pré-conselhos sobre o ambiente de ensino cadastrados no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pré-conselhos encontrados com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum pré-conselho encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<PreConselhoAmbienteEnsinoResponseDTO>> listarPreConselhoAmbienteEnsino() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarTodos());
    }

    @Operation(
            summary = "Busca um pré-conselho pelo ID.",
            description = "Este endpoint retorna as informações de pré-conselho sobre o ambiente de ensino cadastrado no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pré-conselho encontrado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum pré-conselho encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/buscar/{id}")
    public ResponseEntity<PreConselhoAmbienteEnsinoResponseDTO> buscarPreConselhoAmbienteEnsinoPorId(
            @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarPorId(id));
    }

    @Operation(
            summary = "Atualiza um pré-conselho a partir do ID.",
            description = "Este endpoint atualiza as informações de um pré-conselho sobre o ambiente de ensino cadastrado no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pré-conselho atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição."),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum pré-conselho encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<PreConselhoAmbienteEnsinoResponseDTO> atualizarPreConselhoAmbienteEnsinoPorId(
        @Valid @RequestBody PreConselhoAmbienteEnsinoRequestDTO requestDTO,
        @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarPreConselhoAmbienteEnsino(id,requestDTO));
    }

    @Operation(
            summary = "Deleta um pré-conselho a partir do ID.",
            description = "Este endpoint deleta as informações de um pré-conselho sobre o ambiente de ensino cadastrado no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pré-conselho deletado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum pré-conselho encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<PreConselhoAmbienteEnsinoResponseDTO> deletarPreConselhoAmbientePorId(
        @PathVariable Long id
    ){
        service.deletarPreConselhoAmbienteEnsino(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
