package com.conselho.api.controller;

import com.conselho.api.dto.security.AutenticacaoDTO;
import com.conselho.api.dto.security.LoginRespostaDTO;
import com.conselho.api.infra.security.ServiceToken;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.repository.entity.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AutenticacaoController {

    private AuthenticationManager autenticacaoMenager;
    private UsuarioRepository repository;
    private ServiceToken tokenService;

    @Operation(summary = "Login do usuário.", description = "Este endpoint autentica o usuário com e-mail e senha, retornando um token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Credenciais inválidas."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Por favor, tente novamente mais tarde.")
    })

    @PostMapping("/login")
    public ResponseEntity<LoginRespostaDTO> login(
            @RequestBody @Valid AutenticacaoDTO data
    ) {
        var email = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = this.autenticacaoMenager.authenticate(email);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        Long id = ((Usuario) auth.getPrincipal()).getId();
        String emailUsuario = auth.getName();
        String roleUsuario = (((Usuario) auth.getPrincipal()).getRole().getRoleName());
        boolean primeiroAcesso = ((Usuario) auth.getPrincipal()).isPrimeiroAcesso();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new LoginRespostaDTO(id, emailUsuario, roleUsuario, token, primeiroAcesso));
    }
}
