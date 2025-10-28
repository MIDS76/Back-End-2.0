package com.conselho.api.controller;

import com.conselho.api.dto.response.AlunoResponse;
import com.conselho.api.model.aluno.Aluno;
import com.conselho.api.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/api/alunos")
@RestController
@AllArgsConstructor
public class AlunoController {

    private final AlunoService service;


    @Operation(summary = "Cria um novo aluno", description = "Esse endpoint cria um novo aluno no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Aluno criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados de entrada")
    })
    @PostMapping("/criar")
    public ResponseEntity<AlunoResponse> criarAluno(
            @Valid @RequestBody Aluno aluno
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criarAluno(aluno));
    }

    @Operation(summary = "Lista todos os alunos", description = "Esse endpoint retorna todos os alunos cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alunos encontrados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum aluno encontrado")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<AlunoResponse>> obterTodos(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarAlunos());
    }

    @Operation(summary = "Busca um aluno pelo ID", description = "Esse endpoint retorna um aluno específico a partir do ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })

    @GetMapping("/buscar/{id}")
    public ResponseEntity<AlunoResponse> obterAlunoPorId(
            @PathVariable Long idAluno
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarAlunoPorId(idAluno));
    }

    @Operation(summary = "Atualiza um aluno existente", description = "Esse endpoint atualiza as informações de um aluno existente no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados de entrada"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<AlunoResponse> atualizarAluno(
            @PathVariable Long id,
            @Valid @RequestBody Aluno aluno
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarAluno(id,aluno));
    }

    @Operation(summary = "Deleta um aluno", description = "Esse endpoint remove um aluno do sistema pelo ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<AlunoResponse> deletarAluno(
            @PathVariable Long idAluno
    ){
        service.deletarAluno(idAluno);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "Verifica se o aluno é representante", description = "Esse endpoint verifica se o aluno com o ID informado é o representante da turma.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verificação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    @GetMapping("/alunos/representante/{id}")
    public boolean verificarRepresentante(
            @PathVariable Long id
    ){
        return service.isRepresentante(id);
    }

    @Operation(summary = "Obtém o representante da turma", description = "Esse endpoint retorna o aluno que é o representante atual da turma.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Representante encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum representante encontrado")
    })

    @GetMapping("/alunos/representante")
    public Aluno obterRepresentante() {
        return service.getRepresentante();
    }

}
