package com.conselho.api.controller;

import com.conselho.api.dto.request.ConselhoAlunoRequest;
import com.conselho.api.dto.response.AlunoResponse;
import com.conselho.api.dto.response.ConselhoAlunoResponse;
import com.conselho.api.dto.response.ConselhoResponse;
import com.conselho.api.service.ConselhoAlunoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/conselho-alunos")
public class ConselhoAlunoController {

    @Autowired
    private ConselhoAlunoService conselhoAlunoService;

    // BUSCAR ALUNOS POR UM CONSELHO ESPECIFICO
    @GetMapping("/{idConselho}/alunos")
    public ResponseEntity<List<AlunoResponse>> getAlunosPorConselho(
            @RequestParam Long idConselho) {
        return ResponseEntity.status(HttpStatus.OK).body(conselhoAlunoService.getAlunosPorConselho(idConselho));
    }

    @GetMapping("/{idAluno}/conselhos")
    public ResponseEntity<List<ConselhoResponse>> getConselhosPorAluno(
            @RequestParam Long idAluno) {
       return ResponseEntity.status(HttpStatus.OK).body(conselhoAlunoService.getConselhosPorAluno(idAluno));
    }

    @PostMapping("/associar")
    public ResponseEntity<ConselhoAlunoResponse> associarAlunoAConselho(
            @RequestBody @Valid ConselhoAlunoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(conselhoAlunoService.associarAlunoAConselho(request));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ConselhoAlunoResponse>> buscarTodos(){
        return ResponseEntity.status(HttpStatus.OK).body(conselhoAlunoService.buscarTodos());
    }

    @DeleteMapping("deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        conselhoAlunoService.deletarConselhoAluno(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

