package com.conselho.api.controller;

import com.conselho.api.dto.request.AtualizarEtapaRequestDTO;
import com.conselho.api.dto.request.ConselhoRequestDTO;
import com.conselho.api.dto.response.ConselhoFeedbacksResponseDTO;
import com.conselho.api.dto.response.ConselhoResponseDTO;
import com.conselho.api.service.ConselhoService;
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
@RequestMapping("/api/conselhos")
public class ConselhoController {

    private ConselhoService service;

    @Operation(
            summary = "Cria um novo conselho.",
            description = "Este endpoint cria um novo conselho, com base nos dados fornecidos."
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

    @GetMapping("/listar/{id}/alunosFeedbacks")
    public ResponseEntity<ConselhoFeedbacksResponseDTO> listarFeedbacksAlunos(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.listarFeedbacksAlunos(id));

    }
  
    @GetMapping("/listarConselhosPorAluno/{idAluno}")
    public ResponseEntity<List<ConselhoResponseDTO>>listarConselhorPorAluno(
            @PathVariable Long idAluno
    ){
        List<ConselhoResponseDTO> conselhos = service.listarConselhosPorAluno(idAluno);
        return ResponseEntity.status(HttpStatus.OK).body(conselhos);

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

    @Operation(
            summary = "Filtra os conselhos a partir das etapas.",
            description = "Este endpoint filtra todos os conselhos cadastrados a partir da etapa, recebendo apenas as etapas já definidas pela URL."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conselhos filtrados com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Etapa não encontrada no sistema."),
            @ApiResponse(responseCode = "403", description = "Acesso proibido. Verifique suas permissões ou entre em contato com o administrador."),
            @ApiResponse(responseCode = "404", description = "Nenhum conselho encontrado com a etapa fornecida."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @GetMapping("/filtrarPorEtapas")
    public ResponseEntity<List<ConselhoResponseDTO>> filtrarPorEtapas(
            @RequestParam(value = "etapa", required = false, defaultValue = "") String etapa
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.filtrarPorEtapa(etapa));
    }

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

    // QUANDO PRECISAR MUDAR ETAPA | VALIDA PARA TODAS ETAPAS

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
    public ResponseEntity<ConselhoResponseDTO> updateEtapa(@PathVariable Long id, @RequestBody @Valid AtualizarEtapaRequestDTO etapaRequest){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarEtapa(id, etapaRequest));
    }

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

    @GetMapping("/listarConselhorPorTurma/{idTurma}")
    public ResponseEntity<List<ConselhoResponseDTO>> listarTodosConselhoTurma (
        @PathVariable Long idTurma
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarTodosConselhosDeTurma(idTurma));
    }

    @GetMapping("/buscarConselhoPorTurma/{idTurma}")
    public ResponseEntity<ConselhoResponseDTO> buscarConselhoPorTurma(
            @PathVariable Long idTurma
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarConselhoPorTurma(idTurma));
    }
}
