package com.conselho.api.controller;

import com.conselho.api.dto.request.AlunoTurmaRequestDTO;
import com.conselho.api.dto.response.AlunoTurmaResponseDTO;
import com.conselho.api.service.AlunoTurmaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/aluno-turma")
public class AlunoTurmaController {

    private final AlunoTurmaService service;

    @PostMapping("/criar")
    public ResponseEntity<List<AlunoTurmaResponseDTO>> criarAlunoTurma(
            @RequestBody AlunoTurmaRequestDTO request
    ){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criarAlunoTurma(request));
    }

    @GetMapping("/listarAlunosPorTurma/{idTurma}")
    public ResponseEntity<List<String>> listarAlunosPorId(
            @PathVariable Long idTurma
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.listarAlunosPorId(idTurma));
    }
}
