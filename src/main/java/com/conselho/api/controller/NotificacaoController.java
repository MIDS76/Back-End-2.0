package com.conselho.api.controller;

import com.conselho.api.dto.response.NotificacaoResponseDTO;
import com.conselho.api.service.notificacao.NotificacaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/notificacao")
public class NotificacaoController {
    private final NotificacaoService service;
    @GetMapping("/listar/{usuarioId}")
    public ResponseEntity<List<NotificacaoResponseDTO>> buscarTodos (@PathVariable Long usuarioId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarTodasPorUsuario(usuarioId));
    }

    @PatchMapping("/lida/{notificacaoId}")
    public ResponseEntity<NotificacaoResponseDTO> marcarComoLida (@PathVariable Long notificacaoId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.marcarLida(notificacaoId));
    }
}