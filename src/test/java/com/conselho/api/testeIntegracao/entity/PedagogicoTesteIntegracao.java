package com.conselho.api.testeIntegracao.entity;

import com.conselho.api.dto.request.entity.PedagogicoRequestDTO;
import com.conselho.api.dto.response.entity.PedagogicoResponseDTO;
import com.conselho.api.model.entity.Pedagogico;
import com.conselho.api.model.usuario.UsuarioRole;
import com.conselho.api.repository.entity.PedagogicoRepository;
import com.conselho.api.repository.entity.UsuarioRepository;
import com.conselho.api.service.entity.PedagogicoService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class PedagogicoTesteIntegracao {

    private final PedagogicoService pedagogicoService;
    private final PedagogicoRepository pedagogicoRepository;

    public PedagogicoTesteIntegracao (
            PedagogicoService pedagogicoService,
            PedagogicoRepository pedagogicoRepository
    ) {
        this.pedagogicoService = pedagogicoService;
        this.pedagogicoRepository = pedagogicoRepository;
    }
    private Pedagogico pedagogico;

    @BeforeEach
    void setup() {
        pedagogico = new Pedagogico();
        pedagogico.setNome("Pedagogico Teste");
        pedagogico.setEmail("pedagogico@gmail.com");
        pedagogico.setSenha("pedago123");
        pedagogico.setRole(UsuarioRole.PEDAGOGICO);

        pedagogicoRepository.save(pedagogico);
    }

    @Test
    void deveListarTodosPedagogicosComSucesso() {
        var pedagogico = pedagogicoService.listarPedagogico();

        assertThat(pedagogico).isNotEmpty();
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
        PedagogicoRequestDTO request = new PedagogicoRequestDTO("Pedagogico", "pedagogico1@gmail.com");

        pedagogicoService.atualizarPedagogico(pedagogico.getId(), request);

        Pedagogico updatedPedagogico = pedagogicoRepository.findById(pedagogico.getId()).orElseThrow();
        assertThat(updatedPedagogico.getNome()).isEqualTo("Pedagogico");
        assertThat(updatedPedagogico.getEmail()).isEqualTo("pedagogico1@gmail.com");
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
        pedagogico2.setEmail("pedagogico2@gmail.com");
        pedagogico2.setSenha("pedago123");
        pedagogico2.setRole(UsuarioRole.PEDAGOGICO);
        pedagogicoRepository.save(pedagogico2);

        PedagogicoRequestDTO request = new PedagogicoRequestDTO("Pedagogico", "pedagogico2@gmail.com");

        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () ->
                pedagogicoService.atualizarPedagogico(pedagogico.getId(), request)
        );
    }
}