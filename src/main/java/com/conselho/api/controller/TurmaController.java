package com.conselho.api.controller;

import com.conselho.api.dto.request.TurmaRequestDTO;
import com.conselho.api.dto.response.TurmaResponseDTO;
import com.conselho.api.service.TurmaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/api/turmas")
@RestController
@AllArgsConstructor
public class TurmaController {

    private TurmaService service;
    @PostMapping("/criar")
    public ResponseEntity<TurmaResponseDTO> criarTurma(
          @Valid @RequestBody TurmaRequestDTO request
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criarTurma(request));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<TurmaResponseDTO>> listarTurmas(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarTurmas());
    }

    @GetMapping("/buscar/{idTurma}")
    public ResponseEntity<TurmaResponseDTO> buscarTurmaPorId(
            @PathVariable Long idTurma
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarTurmaPorId(idTurma));
    }

    @GetMapping("/ordemAlfabetica")
    public ResponseEntity<List<TurmaResponseDTO>> ordemAlfabetica(
            @RequestParam(value = "ordem", required = false, defaultValue = "Z-A") String ordem
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.ordenarTurmaOrdemAlfabetica(ordem));
    }

    @GetMapping("/filtrarPorCurso")
    public ResponseEntity<List<TurmaResponseDTO>> filtrarPorCurso(
            @RequestParam(value = "curso", required = false, defaultValue = "") String curso
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.filtrarPorCurso(curso));
    }

    @GetMapping("/filtrarPorAnoEntrada")
    public ResponseEntity<List<TurmaResponseDTO>> filtrarPorAnoEntrada(
            @RequestParam(value = "anoEntrada", required = false) Long anoEntrada
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.filtrarPorAno(anoEntrada));
    }

    @GetMapping("/listarAnosDeEntrada")
    public ResponseEntity<List<Integer>> listarAnosDeEntrada(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarAnosDeEntrada());
    }

    @GetMapping("/listarCursos")
    public ResponseEntity<List<String>> listarCursos(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarCurso());
    }

    @PutMapping("/atualizar/{idTurma}")
    public ResponseEntity<TurmaResponseDTO> atualizarTurma(
            @PathVariable Long idTurma,
            @Valid @RequestBody TurmaRequestDTO request
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizarTurma(idTurma,request));
    }

    @DeleteMapping("/deletar/{idTurma}")
    public ResponseEntity<Void> deletarTurma(
            @PathVariable Long idTurma
    ){
        service.deletarTurma(idTurma);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
