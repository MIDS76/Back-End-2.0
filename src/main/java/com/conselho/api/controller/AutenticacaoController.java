package com.conselho.api.controller;

import com.conselho.api.dto.security.AutenticacaoDTO;
import com.conselho.api.dto.security.CadastroDTO;
import com.conselho.api.dto.security.LoginRespostaDTO;
import com.conselho.api.infra.security.ServiceToken;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AutenticacaoController {

    private AuthenticationManager autenticacaoMenager;
    private UsuarioRepository repository;
    private ServiceToken tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginRespostaDTO> login(
            @RequestBody @Valid AutenticacaoDTO data
    ) {
        var email = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = this.autenticacaoMenager.authenticate(email);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new LoginRespostaDTO(token));
    }

//    @PostMapping("/cadastrar")
//    public ResponseEntity<Void> cadastrar(
//            @RequestBody @Valid CadastroDTO data
//    ) {
//        if(this.repository.findByEmail(data.email()) != null){
//            return ResponseEntity.badRequest().build();
//        }
//
//        String senhaCriptografada = new BCryptPasswordEncoder().encode(data.senha());
//        String role = data.role().getRoleName();
//        Usuario newUsuario = new Usuario(data.nome(), data.email(), senhaCriptografada, role);
//
//        repository.save(newUsuario);
//        return ResponseEntity.status(HttpStatus.OK).build();
//
//    }
}
