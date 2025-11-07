package com.conselho.api.controller;

import com.conselho.api.model.UcProfessor;
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
    public ResponseEntity<UcProfessor> criarUcTurma(
            @RequestParam Long idConselho,
            @RequestParam Long idProfessor,
            @RequestParam Long idUnidadeCurricular
    ){
        UcProfessor ucProfessor = ucTurmaService.criarUcTurma(idConselho, idProfessor, idUnidadeCurricular);
        return ResponseEntity.ok(ucProfessor);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UcProfessor> atualizarUcTurma(
            @PathVariable Long id,
            @RequestParam Long idConselho,
            @RequestParam Long idProfessor,
            @RequestParam Long idUnidadeCurricular
    ){
        UcProfessor ucProfessorAtualizada = ucTurmaService.atualizarUcTurma(id, idConselho, idProfessor, idUnidadeCurricular);
        return ResponseEntity.ok(ucProfessorAtualizada);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<UcProfessor> buscarPorId(
            @PathVariable Long id
    ){
        UcProfessor ucProfessor = ucTurmaService.buscarUcTurmaPorId(id);
        return ResponseEntity.ok(ucProfessor);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UcProfessor>> listarTodas() {
        List<UcProfessor> ucProfessors = ucTurmaService.listarUcTurma();
        return ResponseEntity.ok(ucProfessors);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarUcTurma(
            @PathVariable Long id
    ){
        ucTurmaService.deletarUcTurma(id);
        return ResponseEntity.noContent().build();
    }
}
