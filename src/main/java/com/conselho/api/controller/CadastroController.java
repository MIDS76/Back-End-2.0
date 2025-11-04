package com.conselho.api.controller;

import com.conselho.api.dto.request.AlunoRequestDTO;
import com.conselho.api.dto.request.PedagogicoRequestDTO;
import com.conselho.api.dto.request.ProfessorRequestDTO;
import com.conselho.api.dto.request.SupervisorRequestDTO;
import com.conselho.api.dto.response.AlunoResponseDTO;
import com.conselho.api.dto.response.PedagogicoResponseDTO;
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
    public ResponseEntity<AlunoResponseDTO> cadastroAluno(
            @RequestBody @Valid AlunoRequestDTO request
    ){
        service.cadastrarAluno(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
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

}
