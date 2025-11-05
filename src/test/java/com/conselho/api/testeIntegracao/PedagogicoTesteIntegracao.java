package com.conselho.api.testeIntegracao;

import com.conselho.api.dto.request.PedagogicoRequestDTO;
import com.conselho.api.dto.response.PedagogicoResponseDTO;
import com.conselho.api.model.Pedagogico;
import com.conselho.api.model.usuario.UsuarioRole;
import com.conselho.api.repository.PedagogicoRepository;
import com.conselho.api.repository.UsuarioRepository;
import com.conselho.api.service.PedagogicoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PedagogicoTesteIntegracao {
    @Autowired
    private PedagogicoService pedagogicoService;

    @Autowired
    private PedagogicoRepository pedagogicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Pedagogico pedagogico;

    @BeforeEach
    void setup() {
        pedagogico = new Pedagogico();
        pedagogico.setNome("Pedagogico Teste");
        pedagogico.setEmail("pedagogico@gmail.com");
        pedagogico.setSenha("peda123");
        pedagogico.setRole(UsuarioRole.PEDAGOGICO);
        pedagogicoRepository.save(pedagogico);
    }

    @Test
    void deveListarTodosPedagogicosComSucesso() {
        List<PedagogicoResponseDTO> pedagogicos = pedagogicoService.listarPedagogico();

        assertThat(pedagogicos).isNotEmpty();
        assertThat(pedagogicos.get(0).nome()).isEqualTo("Pedagogico Teste");
    }

    @Test
    void deveBuscarPedagogicoPorIdComSucesso() {
        PedagogicoResponseDTO response = pedagogicoService.buscarPedagogicoPorId(pedagogico.getId());

        assertThat(response).isNotNull();
        assertThat(response.nome()).isEqualTo("Pedagogico Teste");
        assertThat(response.email()).isEqualTo("pedagogico@gmail.com");
    }

    @Test
    void deveAtualizarPedagogicoComSucesso() {
        PedagogicoRequestDTO request = new PedagogicoRequestDTO("Pedagogico", "pedagogico1@gmail.com", "peda1234");

        pedagogicoService.atualizarPedagogico(pedagogico.getId(), request);

        Pedagogico updatedPedagogico = pedagogicoRepository.findById(pedagogico.getId()).orElseThrow();
        assertThat(updatedPedagogico.getNome()).isEqualTo("Pedagogico");
        assertThat(updatedPedagogico.getEmail()).isEqualTo("pedagogico1@test.com");
    }

    @Test
    void deveDeletarPedagogicoComSucesso() {
        Long id = pedagogico.getId();
        pedagogicoService.deletarPedagogico(id);

        assertThat(pedagogicoRepository.findById(id)).isEmpty();
    }

    @Test
    void naoDeveAtualizarPedagogicoComEmailDuplicado() {
        Pedagogico pedagogico2 = new Pedagogico();
        pedagogico2.setNome("Pedagogico 2");
        pedagogico2.setEmail("pedagogico2@test.com");
        pedagogico2.setSenha("pedago123");
        pedagogico2.setRole(UsuarioRole.PEDAGOGICO);
        pedagogicoRepository.save(pedagogico2);

        PedagogicoRequestDTO request = new PedagogicoRequestDTO("Pedagogico", "pedagogico2@gmail.com", "pedago1234");

        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () ->
                pedagogicoService.atualizarPedagogico(pedagogico.getId(), request)
        );
    }
}