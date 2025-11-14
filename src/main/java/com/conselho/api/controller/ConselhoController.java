package com.conselho.api.controller;

import com.conselho.api.dto.request.AtualizarEtapaRequestDTO;
import com.conselho.api.dto.request.ConselhoRequestDTO;
import com.conselho.api.dto.response.ConselhoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import com.conselho.api.service.ConselhoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "Conselhos", description = "Endpoints para gerenciamento de conselhos")
@RequestMapping("/api/conselhos")
public class ConselhoController {
    private ConselhoService service;

    @Operation(
            summary = "Cria um novo feedback individual para os alunos.",
            description = "Este endpoint cria um novo feedback para cada conselho cadastrado no sistema, com base nos dados fornecidos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Conselho criado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhuma turma encontrada para criar o conselho."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PostMapping("/criar")
    public ResponseEntity<ConselhoResponseDTO> create(@RequestBody @Valid ConselhoRequestDTO request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarConselho(request));
    }


    @Operation(
            summary = "Lista todos os conselhos.",
            description = "Este endpoint retorna todos os conselhos cadastrados no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conselhos encontrados com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum conselho encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<ConselhoResponseDTO>> listarConselhos(){
        return ResponseEntity.status(HttpStatus.OK).body(service.listarConselhos());
    }

    @Operation(
            summary = "Busca um conselho pelo ID.",
            description = "Este endpoint retorna as informações de um conselho cadastrado no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conselho encontrado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum conselho encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ConselhoResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarConselhoPorId(id));
    }

<<<<<<< HEAD
=======

    @GetMapping("/filtrarPorEtapas")
    public ResponseEntity<List<ConselhoResponseDTO>> filtrarPorEtapas(
            @RequestParam(value = "etapa", required = false, defaultValue = "") String etapa
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.filtrarPorEtapa(etapa));
    }

>>>>>>> f139fd02d026a6026f10af8f9a3269c82bfd896f
    @Operation(
            summary = "Atualiza um conselho a partir do ID.",
            description = "Este endpoint atualiza as informações de um conselho cadastrado no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conselho atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição."),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum conselho encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ConselhoResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ConselhoRequestDTO request){
        return ResponseEntity.status(HttpStatus.OK).body(service.atualizarConselho(id, request));
    }

<<<<<<< HEAD
=======
    // QUANDO PRECISAR MUDAR ETAPA | VALIDA PARA TODAS ETAPAS
>>>>>>> f139fd02d026a6026f10af8f9a3269c82bfd896f
    @Operation(
            summary = "Atualiza a etapa de um processo a partir do ID.",
            description = "Este endpoint permite a atualização da etapa de um processo, incluindo o nome da nova etapa e as datas previstas de início e fim, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Etapa atualizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos no corpo da requisição."),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum processo encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PatchMapping("/atualizar/{id}/etapa")
    public ResponseEntity<ConselhoResponseDTO> updateEtapa(@PathVariable Long id, @RequestBody @Valid AtualizarEtapaRequestDTO request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarEtapa(
                        id,
                        request.novaEtapa(),
                        request.dataInicioPre(),
                        request.dataFimPre()));
    }

<<<<<<< HEAD
=======

>>>>>>> f139fd02d026a6026f10af8f9a3269c82bfd896f
    @Operation(
            summary = "Deleta um conselho a partir do ID.",
            description = "Este endpoint deleta as informações de um conselho cadastrado no sistema, baseado no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Conselho deletado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum conselho encontrado com o ID fornecido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })
    @DeleteMapping("deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deletarConselho(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
