package com.conselho.api.controller;

import com.conselho.api.dto.request.*;
import com.conselho.api.dto.request.entity.AlunoRequestDTO;
import com.conselho.api.dto.request.entity.PedagogicoRequestDTO;
import com.conselho.api.dto.request.entity.ProfessorRequestDTO;
import com.conselho.api.dto.request.entity.WegRequestDTO;
import com.conselho.api.dto.response.entity.AlunoResponseDTO;
import com.conselho.api.dto.response.entity.PedagogicoResponseDTO;
import com.conselho.api.dto.response.entity.UsuarioResponseDTO;
import com.conselho.api.service.CadastroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth/cadastrar")
@Tag(name = "Cadastro", description = "Endpoints para cadastro de alunos, professores, supervisores e pedagógicos")
public class CadastroController {

    private final CadastroService service;

    @Operation(summary = "Cadastro de um novo aluno.", description = "Cadastra um novo aluno no sistema com base nas informações enviadas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Aluno cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PostMapping("/alunos")
    public ResponseEntity<AlunoResponseDTO> cadastroAluno(
            @RequestBody @Valid AlunoRequestDTO request
    ){
        service.cadastrarAluno(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "Cadastra múltiplos novos alunos.", description = "Este endpoint permite o cadastro de múltiplos alunos de uma só vez. Cada aluno será cadastrado com base nos dados fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alunos cadastrados com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PostMapping("/listaAlunos")
    public ResponseEntity<Void> cadastrarAlunos(@RequestBody ArrayList<AlunoRequestDTO> alunosRequest) {
        for (AlunoRequestDTO request : alunosRequest) {
            service.cadastrarAluno(request);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Cadastro de um novo membro do pedagógico.", description = "Cadastra um novo membro do pedagógico no sistema com base nas informações enviadas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedagógico cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PostMapping("/pedagogico")
    public ResponseEntity<PedagogicoResponseDTO> cadastroPedagogico(
            @RequestBody @Valid PedagogicoRequestDTO request
            ){

        service.cadastroPedagogico(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }


    @Operation(summary = "Cadastro de um novo professor.", description = "Cadastra um novo professor no sistema com base nas informações enviadas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Professor cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PostMapping("/professor")
    public ResponseEntity<PedagogicoResponseDTO> cadastroProfessor(
            @RequestBody @Valid ProfessorRequestDTO request
    ){

        service.cadastroProfessor(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "Cadastro de um novo supervisor.", description = "Cadastra um novo supervisor no sistema com base nas informações enviadas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Supervisor cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PostMapping("/supervisor")
    public ResponseEntity<PedagogicoResponseDTO> cadastroSupervisor(
            @RequestBody @Valid SupervisorRequestDTO request
    ){

        service.cadastroSupervisor(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "Cadastro de um novo colaborador Weg.", description = "Cadastra um novo colaborador WEG no sistema com base nas informações enviadas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Colaborador WEG cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PostMapping("/weg")
    public ResponseEntity<UsuarioResponseDTO> cadastroWeg(
            @RequestBody @Valid WegRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.cadastroWeg(request));
    }
}
