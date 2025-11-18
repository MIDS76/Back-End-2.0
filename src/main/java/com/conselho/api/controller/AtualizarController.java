package com.conselho.api.controller;

import com.conselho.api.service.AtualizarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/atualizar")
@AllArgsConstructor
public class AtualizarController {

    private AtualizarService service;

    @Operation(
            summary = "Atualiza a senha de um usuário a partir do ID.",
            description = "Este endpoint atualiza a senha de um usuário cadastrado no sistema, baseado no ID fornecido. Para realizar a alteração de senha, é necessário incluir a chave-valor { \\\"senha\\\": \\\"**senha desejada**\\\" } na requisição."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição."),
            @ApiResponse(responseCode = "404", description = "Nenhum usuário encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PatchMapping("/senha/{id}")
    public ResponseEntity<Void> atualizarSenha(
            @PathVariable Long id,
            @RequestBody Map<String, String> camposAtualizacao) {

        service.atualizarSenha(id, camposAtualizacao);
        return ResponseEntity.status(HttpStatus.OK)
                .build();

    }

    @Operation(
            summary = "Atualiza o representante a partir do ID.",
            description = "Este endpoint permite a atualização parcial das informações de um representante cadastrado no sistema, baseado no ID fornecido. Caso o usuário deseje alterar o representante, é necessário incluir a chave-valor { \\\"representante\\\": boolean } na requisição."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Representante atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição."),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum representante encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PatchMapping("/representante/{id}")
    public ResponseEntity<Void> atualizarRepresentante(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> camposAtualizacao
    ){
        service.atualizarRepresentante(id, camposAtualizacao);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @Operation(
            summary = "Atualiza a atividade de um usuário a partir do ID.",
            description = "Este endpoint atualiza a atividade de um usuário cadastrado no sistema, baseado no ID fornecido, retornando um boolean."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atividade atualizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição."),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum usuário encontrado para atualizar seu status."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PatchMapping("/atividade/{id}")
    public ResponseEntity<Void> atualizarAtividadeDoUsuario(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> camposAtualizacao
    ){
        service.atualizarAtividade(id, camposAtualizacao);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
