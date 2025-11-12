package com.conselho.api.controller;

import com.conselho.api.model.UcProfessor;
import com.conselho.api.service.UcTurmaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ucturma")
@AllArgsConstructor
public class UcTurmaController {

    private UcTurmaService ucTurmaService;


    @Operation(
            summary = "Cria uma nova UC para um professor.",
            description = "Este endpoint cria uma nova UC para o professor, associando-a a um conselho e unidade curricular."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "UC Professor criada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos na requisição."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Tente novamente mais tarde.")
    })

    @PostMapping("/criar")
    public ResponseEntity<UcProfessor> criarUcTurma(
            @RequestParam Long idConselho,
            @RequestParam Long idProfessor,
            @RequestParam Long idUnidadeCurricular
    ){
        UcProfessor ucProfessor = ucTurmaService.criarUcTurma(idConselho, idProfessor, idUnidadeCurricular);
        return ResponseEntity.ok(ucProfessor);
    }

    @Operation(
            summary = "Atualiza uma UC para um professor.",
            description = "Este endpoint atualiza a associação de uma UC para um professor, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UC Professor atualizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos na requisição."),
            @ApiResponse(responseCode = "404", description = "UC Professor não encontrada com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Tente novamente mais tarde.")
    })

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UcProfessor> atualizarUcTurma(
            @PathVariable Long id,
            @RequestParam Long idConselho,
            @RequestParam Long idProfessor,
            @RequestParam Long idUnidadeCurricular
    ){
        UcProfessor ucProfessorAtualizada = ucTurmaService.atualizarUcTurma(id, idConselho, idProfessor, idUnidadeCurricular);
        return ResponseEntity.ok(ucProfessorAtualizada);
    }

    @Operation(
            summary = "Busca uma UC para um professor pelo ID.",
            description = "Este endpoint retorna as informações da UC de um professor, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UC Professor encontrado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "UC Professor não encontrada com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Tente novamente mais tarde.")
    })

    @GetMapping("/buscar/{id}")
    public ResponseEntity<UcProfessor> buscarPorId(
            @PathVariable Long id
    ){
        UcProfessor ucProfessor = ucTurmaService.buscarUcTurmaPorId(id);
        return ResponseEntity.ok(ucProfessor);
    }

    @Operation(
            summary = "Lista todas as UC de professores.",
            description = "Este endpoint retorna todas as UC associadas a professores no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UC Professores encontrados com sucesso!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Tente novamente mais tarde.")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<UcProfessor>> listarTodas() {
        List<UcProfessor> ucProfessors = ucTurmaService.listarUcTurma();
        return ResponseEntity.ok(ucProfessors);
    }

    @Operation(
            summary = "Deleta uma UC de professor pelo ID.",
            description = "Este endpoint deleta a UC de um professor, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "UC Professor deletada com sucesso!"),
            @ApiResponse(responseCode = "404", description = "UC Professor não encontrada com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Tente novamente mais tarde.")
    })

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarUcTurma(
            @PathVariable Long id
    ){
        ucTurmaService.deletarUcTurma(id);
        return ResponseEntity.noContent().build();
    }
}
