package com.conselho.api.controller;

import com.conselho.api.dto.request.AlunoRequest;
import com.conselho.api.dto.request.PedagogicoRequest;
import com.conselho.api.dto.request.ProfessorRequest;
import com.conselho.api.dto.request.SupervisorRequest;
import com.conselho.api.dto.response.AlunoResponse;
import com.conselho.api.dto.response.PedagogicoResponse;
import com.conselho.api.dto.security.CadastroDTO;
import com.conselho.api.repository.*;
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
    public ResponseEntity<AlunoResponse> cadastroAluno(
            @RequestBody @Valid AlunoRequest request
    ){
        service.cadastrarAluno(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/pedagogico")
    public ResponseEntity<PedagogicoResponse> cadastroPedagogico(
            @RequestBody @Valid PedagogicoRequest request
            ){

        service.cadastroPedagogico(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/professor")
    public ResponseEntity<PedagogicoResponse> cadastroProfessor(
            @RequestBody @Valid ProfessorRequest request
    ){

        service.cadastroProfessor(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/supervisor")
    public ResponseEntity<PedagogicoResponse> cadastroSupervisor(
            @RequestBody @Valid SupervisorRequest request
    ){

        service.cadastroSupervisor(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

}
