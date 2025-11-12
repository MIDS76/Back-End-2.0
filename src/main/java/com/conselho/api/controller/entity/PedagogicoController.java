package com.conselho.api.controller.entity;

import com.conselho.api.dto.request.entity.PedagogicoRequestDTO;
import com.conselho.api.dto.response.entity.PedagogicoResponseDTO;
import com.conselho.api.service.entity.PedagogicoService;
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
@RequestMapping("/api/pedagogico")
public class PedagogicoController {
    private final PedagogicoService service;

    @Operation(
            summary = "Lista todos os membros do pedagógico.",
            description = "Este endpoint retorna todos os membros do pedagógico cadastrados no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Membros do pedagógico encontrados com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum membro do pedagógico encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<PedagogicoResponseDTO>> listarPedagogico (){
        return ResponseEntity.status(HttpStatus.OK).body(service.listarPedagogico());
    }

    @Operation(
            summary = "Busca um membro do pedagógico pelo ID.",
            description = "Este endpoint retorna as informações de um membro do pedagógico cadastrado no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Membro do pedagógico encontrado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum membro do pedagógico encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/buscar/{id}")
    public ResponseEntity<PedagogicoResponseDTO> buscarPedagogicoPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPedagogicoPorId(id));
    }

    @Operation(
            summary = "Atualiza um membro do pedagógico a partir do ID.",
            description = "Este endpoint atualiza as informações de um membro do pedagógico cadastrado no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Membro do pedagógico atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição."),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum membro do pedagógico encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<PedagogicoResponseDTO> atualizarPedagogico (@PathVariable Long id, @RequestBody @Valid PedagogicoRequestDTO request){

        return ResponseEntity.status(HttpStatus.OK).body(service.atualizarPedagogico(id, request));
    }

    @Operation(
            summary = "Deleta um membro do pedagógico a partir do ID.",
            description = "Este endpoint deleta as informações de um professor cadastrado no sistema, baseado no ID fornecido"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Membro do pedagógico deletado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum membro do pedagógico encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarPedagogico (@PathVariable Long id){
        service.deletarPedagogico(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}