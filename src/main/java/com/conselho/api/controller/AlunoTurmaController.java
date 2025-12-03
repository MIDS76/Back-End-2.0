package com.conselho.api.controller;

import com.conselho.api.dto.request.AlunoTurmaRequestDTO;
import com.conselho.api.dto.response.AlunoTurmaResponseDTO;
import com.conselho.api.service.AlunoTurmaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/aluno-turma")
public class AlunoTurmaController {

    private final AlunoTurmaService service;

    @Operation(
            summary = "Cria um ou mais alunos em turmas.",
            description = "Este endpoint cria alunos e os associa às turmas especificadas, com base nos dados fornecidos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alunos criados com sucesso e associados às turmas!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição."),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PostMapping("/criar")
    public ResponseEntity<List<AlunoTurmaResponseDTO>> criarAlunoTurma(
            @RequestBody AlunoTurmaRequestDTO request
    ){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criarAlunoTurma(request));
    }

    @Operation(
            summary = "Lista os alunos por turma",
            description = "Este endpoint retorna todos os alunos cadastrados no sistema, baseado no ID da turma fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alunos encontrados com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhuma turma encontrada com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/listarAlunosPorTurma/{idTurma}")
    public ResponseEntity<List<Map<String, Object>>> listarAlunosPorId(
            @PathVariable Long idTurma
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonList(service.listarAlunosPorId(idTurma)));
    }
}
