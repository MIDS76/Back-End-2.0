package com.conselho.api.controller.entity;

import com.conselho.api.dto.response.entity.UsuarioResponseDTO;
import com.conselho.api.serviceTestes.entity.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/usuario")
public class UsuarioController {

    private UsuarioService service;

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarUsuarios());
    }

    @GetMapping("/buscar/{idUsuario}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorId(
            @PathVariable Long idUsuario
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarUsuarioPorId(idUsuario));
    }


    @GetMapping("/buscarAtividade")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarAtividade(
            @RequestParam(value = "ativo", required = false, defaultValue = "true") boolean ativo
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.buscarAtividade(ativo));
    }

    @GetMapping("/ordemAlfabetica")
    public ResponseEntity<List<UsuarioResponseDTO>> ordemAlfabetica(
            @RequestParam(value = "ordem", required = false, defaultValue = "Z-A") String ordem
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.ordenarUsuariosOrdemAlfabetica(ordem));
    }

    @GetMapping("/bucarRole")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarPorRole(
            @RequestParam(value = "role", required = false, defaultValue = "") String role
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.bucarPorRole(role));
    }
}
