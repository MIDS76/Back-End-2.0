package com.conselho.api.controller;

import com.conselho.api.dto.request.*;
import com.conselho.api.dto.request.entity.AlunoRequestDTO;
import com.conselho.api.dto.request.entity.PedagogicoRequestDTO;
import com.conselho.api.dto.request.entity.ProfessorRequestDTO;
import com.conselho.api.dto.request.entity.WegRequestDTO;
import com.conselho.api.dto.response.entity.AlunoResponseDTO;
import com.conselho.api.dto.response.entity.PedagogicoResponseDTO;
import com.conselho.api.dto.response.entity.UsuarioResponseDTO;
import com.conselho.api.service.CadastroService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth/cadastrar")
public class CadastroController {

    private final CadastroService service;

    @PostMapping("/alunos")
    public ResponseEntity<AlunoResponseDTO> cadastroAluno(
            @RequestBody @Valid AlunoRequestDTO request
    ){
        service.cadastrarAluno(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/listaAlunos")
    public ResponseEntity<Void> cadastrarAlunos(@RequestBody ArrayList<AlunoRequestDTO> alunosRequest) {
        for (AlunoRequestDTO request : alunosRequest) {
            service.cadastrarAluno(request);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/pedagogico")
    public ResponseEntity<PedagogicoResponseDTO> cadastroPedagogico(
            @RequestBody @Valid PedagogicoRequestDTO request
            ){

        service.cadastroPedagogico(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/professor")
    public ResponseEntity<PedagogicoResponseDTO> cadastroProfessor(
            @RequestBody @Valid ProfessorRequestDTO request
    ){

        service.cadastroProfessor(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/supervisor")
    public ResponseEntity<PedagogicoResponseDTO> cadastroSupervisor(
            @RequestBody @Valid SupervisorRequestDTO request
    ){

        service.cadastroSupervisor(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/weg")
    public ResponseEntity<UsuarioResponseDTO> cadastroWeg(
            @RequestBody @Valid WegRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.cadastroWeg(request));
    }

    @PostMapping("/admin")
    public ResponseEntity<Void> cadastroAdmin(
            @RequestBody @Valid WegRequestDTO request
    ){
        service.cadastroAdmin(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }
}
