package com.conselho.api.controller.preConselho;


import com.conselho.api.dto.request.preConselho.PreConselhoProfessorRequestDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoProfessorResponseDTO;
import com.conselho.api.service.preConselho.PreConselhoProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/preConselhoProfessor")
public class PreConselhoProfessorController {

    private PreConselhoProfessorService service;

    @Operation(
            summary = "Cria um novo pré-conselho sobre os professores.",
            description = "Este endpoint cria um pré-conselho sobre os professores, com base nos dados fornecidos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pré-conselho criado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum professor encontrado para criar o pré-conselho."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PostMapping("/criar")
    public ResponseEntity<PreConselhoProfessorResponseDTO> criarPreConselhoProfessor(
             @PathVariable Long idConselho, @PathVariable Long idPreConselho
            ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criarPreConselhoProfessor(idConselho, idPreConselho));
    }

    @Operation(
            summary = "Lista todos os pré-conselhos sobre os professores.",
            description = "Este endpoint retorna todos os pré-conselhos sobre os professores cadastrados no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pré-conselhos encontrados com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum pré-conselho encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<PreConselhoProfessorResponseDTO>> listarPreConselhoProessor(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarPreConselhoProfessor());
    }

    @Operation(
            summary = "Busca um pré-conselho pelo ID.",
            description = "Este endpoint retorna as informações de pré-conselho sobre os professores cadastrado no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pré-conselho encontrado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum pré-conselho encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/buscar/{id}")
    public ResponseEntity<PreConselhoProfessorResponseDTO> buscarPreConselhoProfessorPorId(
        @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarPreConselhoProfessorPorId(id));
    }

    @Operation(
            summary = "Atualiza um pré-conselho a partir do ID.",
            description = "Este endpoint atualiza as informações de um pré-conselho sobre os professores cadastrado no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pré-conselho atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição."),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum pré-conselho encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<PreConselhoProfessorResponseDTO> atualizarPreConselhoProfessorPorId(
        @PathVariable Long id,
        @Valid @RequestBody PreConselhoProfessorRequestDTO requestDTO
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarPreConselhoProfessor(id,requestDTO));
    }

    @Operation(
            summary = "Deleta um pré-conselho a partir do ID.",
            description = "Este endpoint deleta as informações de um pré-conselho sobre os professores cadastrado no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pré-conselho deletado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum pré-conselho encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<PreConselhoProfessorResponseDTO> deletarPreConselhoProfessorPorId(
        @PathVariable Long id
    ){
        service.deletarConselho(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

}
