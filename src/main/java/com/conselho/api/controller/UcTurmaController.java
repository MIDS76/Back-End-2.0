package com.conselho.api.controller;

import com.conselho.api.model.UcTurma;
import com.conselho.api.service.UcTurmaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ucturma")
@AllArgsConstructor
public class UcTurmaController {

    private UcTurmaService ucTurmaService;

    @PostMapping("/criar")
    public ResponseEntity<UcTurma> criarUcTurma(
            @RequestParam Long idConselho,
            @RequestParam Long idProfessor,
            @RequestParam Long idUnidadeCurricular
    ){
        UcTurma ucTurma = ucTurmaService.criarUcTurma(idConselho, idProfessor, idUnidadeCurricular);
        return ResponseEntity.ok(ucTurma);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UcTurma> atualizarUcTurma(
            @PathVariable Long id,
            @RequestParam Long idConselho,
            @RequestParam Long idProfessor,
            @RequestParam Long idUnidadeCurricular
    ){
        UcTurma ucTurmaAtualizada = ucTurmaService.atualizarUcTurma(id, idConselho, idProfessor, idUnidadeCurricular);
        return ResponseEntity.ok(ucTurmaAtualizada);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<UcTurma> buscarPorId(
            @PathVariable Long id
    ){
        UcTurma ucTurma = ucTurmaService.buscarUcTurmaPorId(id);
        return ResponseEntity.ok(ucTurma);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UcTurma>> listarTodas() {
        List<UcTurma> ucTurmas = ucTurmaService.listarUcTurma();
        return ResponseEntity.ok(ucTurmas);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarUcTurma(
            @PathVariable Long id
    ){
        ucTurmaService.deletarUcTurma(id);
        return ResponseEntity.noContent().build();
    }
}
