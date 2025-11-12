package com.conselho.api.controller;


import com.conselho.api.dto.request.UnidadeCurricularRequestDTO;
import com.conselho.api.dto.response.UnidadeCurricularResponseDTO;
import com.conselho.api.service.UnidadeCurricularService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/unidadeCurricular")
@AllArgsConstructor
@Tag(name = "Unidades Curriculares", description = "Endpoints para gerenciamento de unidades curriculares")
public class UnidadeCurricularController {

    private final UnidadeCurricularService service;

    @Operation(
            summary = "Cria uma nova unidade curricular.",
            description = "Este endpoint cria uma nova unidade curricular com base nos dados fornecidos no corpo da requisição."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Unidade curricular criada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos na requisição."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Tente novamente mais tarde.")
    })

    @PostMapping("/criar")
    public ResponseEntity<UnidadeCurricularResponseDTO> criarUnidadeCurricular(
            @RequestBody UnidadeCurricularRequestDTO unidadeCurricularRequestDTO
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criarUnidadeCurricular(unidadeCurricularRequestDTO));
    }

    @Operation(
            summary = "Lista todas as unidades curriculares.",
            description = "Este endpoint retorna todas unidades curriculares cadastradas no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unidades curriculares encontradas com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhuma unidade curricular encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<UnidadeCurricularResponseDTO>> listarUnidadesCurriculares(
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarUnidadesCurriculares());
    }

    @Operation(
            summary = "Busca uma unidade curricular pelo ID.",
            description = "Este endpoint retorna as informações de uma unidade curricular cadastrada no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unidade curricular encontrada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhuma unidade curricular encontrada com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/buscar/{id}")
    public ResponseEntity<UnidadeCurricularResponseDTO> buscarUnidadesPorId(
            @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarUnidadesPorId(id));
    }

    @Operation(
            summary = "Atualiza uma unidade curricular a partir do ID.",
            description = "Este endpoint atualiza as informações de uma unidade curricular cadastrada no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unidade curricular atualizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição."),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhuma unidade curricular encontrada com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UnidadeCurricularResponseDTO> atualizarUnidadeCurricular(
            @PathVariable Long id,
            @RequestBody UnidadeCurricularRequestDTO unidadeCurricularRequestDTO
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarUnidadeCurricular(id, unidadeCurricularRequestDTO));
    }

    @Operation(
            summary = "Deleta uma unidade curricular a partir do ID.",
            description = "Este endpoint deleta as informações de uma unidade curricular cadastrada no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Unidade curricular deletada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhuma unidade curricular encontrada com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarUnidadeCurricular(
            @PathVariable Long id
    ){
        service.deletarUnidadeCurricular(id);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "Faz upload de arquivo JSON para processar unidades curriculares", description = "Recebe um arquivo JSON contendo unidades curriculares e processa seu conteúdo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo JSON processado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao processar o arquivo JSON")
    })

    @PostMapping("/upload")
    public ResponseEntity<String> processarJson(
            @RequestParam("file")
            MultipartFile file)
    {
        try {
            service.processarJson(file);
            return  ResponseEntity.status(HttpStatus.OK)
                    .body("Arquivo JSON processado com sucesso!");
        } catch (IOException e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao processar o arquivo JSON.");

        }
    }
}