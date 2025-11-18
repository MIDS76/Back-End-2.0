package com.conselho.api.testeIntegracao;

import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.model.usuario.UsuarioRole;
import com.conselho.api.repository.entity.UsuarioRepository;
import com.conselho.api.service.AutorizacaoService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class AutorizacaoTesteIntegracao {
    private final AutorizacaoService autorizacaoService;
    private final UsuarioRepository usuarioRepository;

    private Usuario usuario;

    public AutorizacaoTesteIntegracao(
            AutorizacaoService autorizacaoService,
            UsuarioRepository usuarioRepository
    ){
        this.autorizacaoService = autorizacaoService;
        this.usuarioRepository = usuarioRepository;
    }
    @BeforeEach
    void setup() {
        usuario = new Usuario();
        usuario.setNome("Vinicius");
        usuario.setEmail("vini@gmail.com");
        usuario.setSenha("vini123");
        usuario.setRole(UsuarioRole.PEDAGOGICO);
        usuarioRepository.save(usuario);
    }

    @Test
    void deveCarregarUsuarioPorEmail() {
        UserDetails userDetails = autorizacaoService.loadUserByUsername("vini@gmail.com");

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo("vini@gmail.com");
    }
}
