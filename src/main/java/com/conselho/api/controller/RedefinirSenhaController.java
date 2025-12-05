package com.conselho.api.controller;

import com.conselho.api.dto.request.ConfirmarRedefinicaoRequestDTO;
import com.conselho.api.dto.request.RedefinirSenhaRequestDTO;
import com.conselho.api.dto.response.RedefinirSenhaResponseDTO;
import com.conselho.api.service.RedefinirSenhaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redefinirSenha")
@AllArgsConstructor
public class RedefinirSenhaController {

    private final RedefinirSenhaService senhaService;

    @PostMapping("/solicitar")
    public ResponseEntity<RedefinirSenhaResponseDTO> solicitarRedefinicaoSenha(
            @Valid @RequestBody RedefinirSenhaRequestDTO requestDTO
    ){
        senhaService.solicitarRedefinicao(requestDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/confirmar")
    public ResponseEntity<Void> confirmarNovaSenha(
            @Valid @RequestBody ConfirmarRedefinicaoRequestDTO requestDTO
    ){
        senhaService.confirmarRedefinicao(requestDTO);
        return ResponseEntity.ok()
                .build();
    }
}
