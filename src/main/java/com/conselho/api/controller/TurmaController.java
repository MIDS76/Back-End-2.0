package com.conselho.api.controller;

import com.conselho.api.dto.request.TurmaRequestDTO;
import com.conselho.api.dto.response.TurmaResponseDTO;
import com.conselho.api.service.TurmaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/api/turmas")
@RestController
@AllArgsConstructor
public class TurmaController {

    private TurmaService service;

    @Operation(
            summary = "Cria uma nova turma.",
            description = "Este endpoint cria uma nova turma, com base nos dados fornecidos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Turma criada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PostMapping("/criar")
    public ResponseEntity<TurmaResponseDTO> criarTurma(
          @Valid @RequestBody TurmaRequestDTO request
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criarTurma(request));
    }

    @Operation(
            summary = "Lista todos as turmas.",
            description = "Este endpoint retorna todas as turmas cadastrados no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turmas encontradas com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhuma turma encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<TurmaResponseDTO>> listarTurmas(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarTurmas());
    }

    @Operation(
            summary = "Busca uma turma pelo ID.",
            description = "Este endpoint retorna as informações de uma turma cadastrada no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turma encontrada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhuma turma encontrada com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/buscar/{idTurma}")
    public ResponseEntity<TurmaResponseDTO> buscarTurmaPorId(
            @PathVariable Long idTurma
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarTurmaPorId(idTurma));
    }

    @Operation(
            summary = "Lista todas as turmas por ordem alfabética.",
            description = "Este endpoint retorna todos as turmas cadastradas no sistema por ordem alfabética, recebendo uma String 'A-Z' ou 'Z-A' na URL."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "Turmas encontradas com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhuma turma encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/ordemAlfabetica")
    public ResponseEntity<List<TurmaResponseDTO>> ordemAlfabetica(
            @RequestParam(value = "ordem", required = false, defaultValue = "Z-A") String ordem
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.ordenarTurmaOrdemAlfabetica(ordem));
    }

    @Operation(
            summary = "Filtra as turmas a partir do curso.",
            description = "Este endpoint filtra todas as turmas cadastradas a partir do curso inserido, recebendo o curso na URL, ex 'Programação'"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turmas filtradas com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhuma turma encontrada com o curso fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/filtrarPorCurso")
    public ResponseEntity<List<TurmaResponseDTO>> filtrarPorCurso(
            @RequestParam(value = "curso", required = false, defaultValue = "") String curso
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.filtrarPorCurso(curso));
    }

    @Operation(
            summary = "Filtra as turmas a partir do ano de entrada.",
            description = "Este endpoint filtra todas as turmas cadastradas a partir do ano de entrada inserido, recebendo um Long 'anoEntrada' pela URL, retorna apenas anos cadastrados no banco."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turmas filtradas com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Ano de entrada não encontrado no sistema."),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhuma turma encontrada com o ano de entrada fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/filtrarPorAnoEntrada")
    public ResponseEntity<List<TurmaResponseDTO>> filtrarPorAnoEntrada(
            @RequestParam(value = "anoEntrada", required = false) Long anoEntrada
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.filtrarPorAno(anoEntrada));
    }

    @Operation(
            summary = "Lista os anos de entrada de turmas no sistema.",
            description = "Este endpoint retorna os anos de entrada em que uma turma foi cadastrada no banco."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anos de entrada encontrados com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhuma turma cadastrada no banco."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/listarAnosDeEntrada")
    public ResponseEntity<List<Integer>> listarAnosDeEntrada(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarAnosDeEntrada());
    }

    @Operation(
            summary = "Lista os cursos cadastrados no sistema.",
            description = "Este endpoint retorna todos os cursos cadastrados no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cursos encontrados com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum curso encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")    })

    @GetMapping("/listarCursos")
    public ResponseEntity<List<String>> listarCursos(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarCurso());
    }

    @Operation(
            summary = "Atualiza uma turma a partir do ID.",
            description = "Este endpoint atualiza as informações de uma turma cadastrada no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turma atualizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição."),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhuma encontrada com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PutMapping("/atualizar/{idTurma}")
    public ResponseEntity<TurmaResponseDTO> atualizarTurma(
            @PathVariable Long idTurma,
            @Valid @RequestBody TurmaRequestDTO request
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarTurma(idTurma,request));
    }

    @Operation(
            summary = "Deleta uma turma a partir do ID.",
            description = "Este endpoint deleta as informações de uma turma cadastrada no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Turma deletada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhuma turma encontrada com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @DeleteMapping("/deletar/{idTurma}")
    public ResponseEntity<Void> deletarTurma(
            @PathVariable Long idTurma
    ){
        service.deletarTurma(idTurma);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
