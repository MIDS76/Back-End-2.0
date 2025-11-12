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

@RequestMapping("/api/alunos")
@RestController
@AllArgsConstructor
public class AlunoController {

    private final AlunoService service;

    @GetMapping("/listar")
    public ResponseEntity<List<AlunoResponseDTO>> listarAlunos(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarAlunos());
    }

    @Operation(summary = "Busca um aluno pelo ID", description = "Esse endpoint retorna um aluno específico a partir do ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })

    @GetMapping("/buscar/{idAluno}")
    public ResponseEntity<AlunoResponseDTO> obterAlunoPorId(
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
    public ResponseEntity<AlunoResponseDTO> atualizarAluno(
            @PathVariable Long id,
            @Valid @RequestBody AlunoRequestDTO request
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarAluno(id,request));
    }

    @Operation(summary = "Deleta um aluno", description = "Esse endpoint remove um aluno do sistema pelo ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })

    @DeleteMapping("/deletar/{idAluno}")
    public ResponseEntity<Void> deletarAluno(
            @PathVariable Long idAluno
    ){
        service.deletarAluno(idAluno);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    //Verificar se há necessidade

//    @Operation(summary = "Verifica se o aluno é representante", description = "Esse endpoint verifica se o aluno com o ID informado é o representante da turma.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Verificação realizada com sucesso"),
//            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
//    })
//    @GetMapping("/representante/{idAluno}")
//    public boolean verificarRepresentante(
//            @PathVariable Long id
//    ){
//        return service.isRepresentante(id);
//    }
//
//    @Operation(summary = "Obtém o representante da turma", description = "Esse endpoint retorna o aluno que é o representante atual da turma.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Representante encontrado com sucesso"),
//            @ApiResponse(responseCode = "404", description = "Nenhum representante encontrado")
//    })
//
//    @GetMapping("/representante")
//    public Aluno obterRepresentante() {
//        return service.getRepresentante();
//    }

}
