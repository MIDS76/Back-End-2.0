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

    @GetMapping("/listar/{id}/naoLidas")
    public ResponseEntity<List<NotificacaoResponseDTO>> listarNaoLidas (@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.listarNaoLidas(id));
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<List<NotificacaoResponseDTO>> buscarTodos (@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarTodas(id));
    }

    @PatchMapping("/lida/{id}")
    public ResponseEntity<NotificacaoResponseDTO> marcarComoLida (@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.marcarComoLida(id));
    }
}
