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

    @Operation(summary = "Atualiza a senha de um usuário.", description = "Este endpoint permite a atualização da senha de um usuário, identificando-o pelo ID e passando os campos de atualização no corpo da requisição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou senha não atende aos critérios de segurança."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
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

    @Operation(summary = "Atualiza informações de um representante.", description = "Este endpoint permite a atualização parcial das informações de um representante, identificando-o pelo ID e passando os campos a serem atualizados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Representante atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição."),
            @ApiResponse(responseCode = "404", description = "Representante não encontrado."),
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
}
