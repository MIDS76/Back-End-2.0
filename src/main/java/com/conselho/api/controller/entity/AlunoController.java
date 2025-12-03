package com.conselho.api.controller.entity;

import com.conselho.api.dto.request.entity.AlunoRequestDTO;
import com.conselho.api.dto.response.entity.AlunoResponseDTO;
import com.conselho.api.service.entity.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/aluno")
@RestController
@AllArgsConstructor
public class AlunoController {

    private final AlunoService service;

    @Operation(
            summary = "Lista todos os alunos.",
            description = "Este endpoint retorna todos os alunos cadastrados no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alunos encontrados com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum aluno encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<AlunoResponseDTO>> listarAlunos() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarAlunos());
    }

    @Operation(
            summary = "Busca um aluno pelo ID.",
            description = "Este endpoint retorna as informações de um aluno cadastrado no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno encontrado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum aluno encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/buscar/{idAluno}")
    public ResponseEntity<AlunoResponseDTO> buscarAlunoPorId(
            @PathVariable Long idAluno
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarAlunoPorId(idAluno));
    }

    @Operation(
            summary = "Busca se o aluno está ativo no sistema.",
            description = "Este endpoint retorna se o aluno está ativo no sistema, retornando na URL um boolean."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atividade encontrada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum aluno encontrado para retornar seu status."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/buscarAtividade")
    public ResponseEntity<List<AlunoResponseDTO>> buscarAtividade(
            @RequestParam(value = "ativo", required = false, defaultValue = "true") boolean ativo
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarAtividade(ativo));
    }

    @Operation(
            summary = "Lista os alunos por ordem alfabética.",
            description = "Este endpoint retorna todos os alunos cadastrados no sistema por ordem alfabética, recebendo uma String 'A-Z' ou 'Z-A' na URL."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alunos encontrados com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum aluno encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/ordemAlfabetica")
    public ResponseEntity<List<AlunoResponseDTO>> ordemAlfabetica(
            @RequestParam(value = "ordem", required = false, defaultValue = "Z-A") String ordem
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.ordenarAlunosOrdemAlfabetica(ordem));
    }

    @Operation(
            summary = "Atualiza um aluno a partir do ID.",
            description = "Este endpoint atualiza as informações de um aluno cadastrado no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição."),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum aluno encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<AlunoResponseDTO> atualizarAluno(
            @PathVariable Long id,
            @Valid @RequestBody AlunoRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarAluno(id, request));
    }

    @Operation(
            summary = "Deleta um aluno a partir do ID.",
            description = "Este endpoint deleta as informações de um aluno cadastrado no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Aluno deletado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum aluno encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @DeleteMapping("/deletar/{idAluno}")
    public ResponseEntity<Void> deletarAluno(
            @PathVariable Long idAluno
    ) {
        service.deletarAluno(idAluno);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/deletarEmMassa/{idAluno}")
    public ResponseEntity<List<AlunoResponseDTO>>excluirListaAlunos(
            @RequestBody List<Long>idsAlunos){
        List<AlunoResponseDTO> alunosExcluidos = service.excluirListaAlunos(idsAlunos);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
