package com.conselho.api.controller.entity;

import com.conselho.api.dto.request.entity.AlunoRequestDTO;
import com.conselho.api.dto.response.entity.AlunoResponseDTO;
import com.conselho.api.model.entity.Aluno;
import com.conselho.api.service.entity.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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

//    @PostMapping("/cadastrarLista")
//    public ResponseEntity<?> importarAlunos(
//            @RequestBody List<AlunoRequestDTO> listaAlunos
//    ){
//        service.importarAlunos(listaAlunos);
//        return  ResponseEntity.status(HttpStatus.CREATED).build();
//    }

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
    public ResponseEntity<List<AlunoResponseDTO>> listarAlunos(){
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
    public ResponseEntity<AlunoResponseDTO> obterAlunoPorId(
            @PathVariable Long idAluno
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarAlunoPorId(idAluno));
    }

<<<<<<< HEAD
=======

    @GetMapping("/buscarAtividade")
    public ResponseEntity<List<AlunoResponseDTO>> buscarAtividade(
            @RequestParam(value = "ativo", required = false, defaultValue = "true") boolean ativo
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarAtividade(ativo));
    }

    @GetMapping("/ordemAlfabetica")
    public ResponseEntity<List<AlunoResponseDTO>> ordemAlfabetica(
            @RequestParam(value = "ordem", required = false, defaultValue = "Z-A") String ordem
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.ordenarAlunosOrdemAlfabetica(ordem));
    }


>>>>>>> f139fd02d026a6026f10af8f9a3269c82bfd896f
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
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarAluno(id,request));
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
    public ResponseEntity<AlunoResponseDTO> deletarAluno(
            @PathVariable Long idAluno
    ){
        service.deletarAluno(idAluno);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

<<<<<<< HEAD
    //Verificar se há necessidade

    @Operation(summary = "Verifica se o aluno é representante.", description = "Este endpoint verifica se o aluno com o ID informado é o representante da turma.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verificação realizada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum representante encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/alunos/representante/{idAluno}")
    public boolean verificarRepresentante(
            @PathVariable Long id
    ){
        return service.isRepresentante(id);
    }

    @Operation(summary = "Obtém o representante da turma.", description = "Este endpoint retorna o aluno que é o representante atual da turma.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Representante encontrado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum representante encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/alunos/representante")
    public Aluno obterRepresentante() {
        return service.getRepresentante();
    }
=======
>>>>>>> f139fd02d026a6026f10af8f9a3269c82bfd896f
}
