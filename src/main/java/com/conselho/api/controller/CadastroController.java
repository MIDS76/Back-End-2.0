package com.conselho.api.controller;

import com.conselho.api.dto.request.*;
import com.conselho.api.dto.response.AlunoResponseDTO;
import com.conselho.api.dto.response.PedagogicoResponseDTO;
import com.conselho.api.dto.response.UsuarioResponseDTO;
import com.conselho.api.service.CadastroService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth/cadastrar")
public class CadastroController {

    private final CadastroService service;

    @PostMapping("/alunos")
    public ResponseEntity<UsuarioResponseDTO> cadastroAluno(
            @RequestBody @Valid AlunoRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.cadastrarAluno(request));
    }

    @PostMapping("/pedagogico")
    public ResponseEntity<UsuarioResponseDTO> cadastroPedagogico(
            @RequestBody @Valid PedagogicoRequestDTO request
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.cadastroPedagogico(request));
    }

    @PostMapping("/professor")
    public ResponseEntity<UsuarioResponseDTO> cadastroProfessor(
            @RequestBody @Valid ProfessorRequestDTO request
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.cadastroProfessor(request));
    }

    @PostMapping("/supervisor")
    public ResponseEntity<UsuarioResponseDTO> cadastroSupervisor(
            @RequestBody @Valid SupervisorRequestDTO request
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.cadastroSupervisor(request));
    }

    @PostMapping("/weg")
    public ResponseEntity<UsuarioResponseDTO> cadastroWeg(
            @RequestBody @Valid WegRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.cadastroWeg(request));
    }
}
