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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/aluno-turma")
public class AlunoTurmaController {

    private final AlunoTurmaService service;

    // Criar
    @Operation(summary = "Cria um ou mais alunos em turmas", description = "Este endpoint cria alunos e os associa às turmas especificadas, com base nos dados fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alunos criados com sucesso e associados às turmas!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição.")
    })

    @PostMapping("/criar")
    public ResponseEntity<List<AlunoTurmaResponseDTO>> criarAlunoTurma(
            @RequestBody AlunoTurmaRequestDTO request
    ){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criarAlunoTurma(request));
    }
}
