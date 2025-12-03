package com.conselho.api.controller.entity;

import com.conselho.api.dto.request.entity.UsuarioRequestDTO;
import com.conselho.api.dto.response.entity.AlunoResponseDTO;
import com.conselho.api.dto.response.entity.UsuarioResponseDTO;
import com.conselho.api.service.entity.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/usuario")
public class UsuarioController {

    private UsuarioService service;

    @Operation(
            summary = "Lista todos os usuários.",
            description = "Este endpoint retorna todos os usuários cadastrados no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alunos encontrados com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum aluno encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarUsuarios());
    }

    @Operation(
            summary = "Busca um usuário pelo ID.",
            description = "Este endpoint retorna as informações de um usuário cadastrado no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum usuário encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/buscar/{idUsuario}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorId(
            @PathVariable Long idUsuario
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarUsuarioPorId(idUsuario));
    }

    @Operation(
            summary = "Busca se o usuário está ativo no sistema.",
            description = "Este endpoint retorna se o usuário está ativo no sistema, retornando na URL um boolean."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atividade encontrada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição."),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum usuário encontrado para retornar seu status."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/buscarAtividade")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarAtividade(
            @RequestParam(value = "ativo", required = false, defaultValue = "true") boolean ativo
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarAtividade(ativo));
    }

    @Operation(
            summary = "Lista os usuários por ordem alfabética.",
            description = "Este endpoint retorna todos os usuários cadastrados no sistema por ordem alfabética, recebendo uma String 'A-Z' ou 'Z-A' na URL."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum usuário encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/ordemAlfabetica")
    public ResponseEntity<List<UsuarioResponseDTO>> ordemAlfabetica(
            @RequestParam(value = "ordem", required = false, defaultValue = "Z-A") String ordem
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.ordenarUsuariosOrdemAlfabetica(ordem));
    }

    @Operation(
            summary = "Busca a função do usuário no sistema.",
            description = "Este endpoint retorna a função do usuário no sistema, as permissões dele e o'que ele pode acessar."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Função retornada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum usuário encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/bucarRole")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarPorRole(
            @RequestParam(value = "role", required = false, defaultValue = "") String role
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.bucarPorRole(role));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarUsuario(id, request));
    }
}
