package com.conselho.api.testeIntegracao.preConselho;

import com.conselho.api.dto.request.preConselho.PreConselhoPedagogicoRequestDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoPedagogicoResponseDTO;
import com.conselho.api.model.preConselho.PreConselho;
import com.conselho.api.model.preConselho.PreConselhoPedagogico;
import com.conselho.api.repository.preConselho.PreConselhoPedagogicoRepository;
import com.conselho.api.service.preConselho.PreConselhoPedagogicoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class PreConselhoPedagogicoTesteIntegracao {
    private final PreConselhoPedagogicoService preConselhoPedagogicoService;
    private final PreConselhoPedagogicoRepository preConselhoPedagogicoRepository;

    public PreConselhoPedagogicoTesteIntegracao(
            PreConselhoPedagogicoService preConselhoPedagogicoService,
            PreConselhoPedagogicoRepository preConselhoPedagogicoRepository
    ) {
        this.preConselhoPedagogicoService = preConselhoPedagogicoService;
        this.preConselhoPedagogicoRepository = preConselhoPedagogicoRepository;
    }

    @BeforeEach
    void setup() {
        PreConselhoPedagogico preConselhoPedagogico = new PreConselhoPedagogico();

        preConselhoPedagogico.setPreConselho(new PreConselho());
        preConselhoPedagogico.setPontosPositivos("Compreensão");
        preConselhoPedagogico.setPontosMelhoria("Avisos");
        preConselhoPedagogico.setSugestoes("Melhorar tempo dos avisos");

        preConselhoPedagogicoRepository.save(preConselhoPedagogico);
    }

    @Test
    void deveCriarPreConselhoPedagogico() {
        PreConselhoPedagogicoRequestDTO requestDTO = new PreConselhoPedagogicoRequestDTO(1L, "Compreensão", "Avisos", "Melhorar tempo de avisos");
        PreConselhoPedagogicoResponseDTO responseDTO = preConselhoPedagogicoService.criarPreConselhoPedagogico(requestDTO);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.id()).isNotNull();
        assertThat(responseDTO.idPreConselho()).isNotNull();
        assertThat(responseDTO.pontosPositivos());
        assertThat(responseDTO.pontosMelhoria());
        assertThat(responseDTO.sugestoes());
    }

    @Test
    void deveListarPreConselhoPedagogicoComSucesso() {
        var preConselhoPedagogico = preConselhoPedagogicoService.listarTodos();

        assertThat(preConselhoPedagogico).isNotNull();
    }

    @Test
    void deveBuscarPreConselhoPedagogicoPorIdComSucesso() {
        PreConselhoPedagogicoRequestDTO requestDTO = new PreConselhoPedagogicoRequestDTO(1L, "Compreensao", "Avisos", "Melhorar tempo de avisos");
        PreConselhoPedagogicoResponseDTO responseDTO = preConselhoPedagogicoService.criarPreConselhoPedagogico(requestDTO);
        PreConselhoPedagogicoResponseDTO buscarPorId = preConselhoPedagogicoService.buscarPorId(responseDTO.id());

        assertThat(buscarPorId).isNotNull();
        assertThat(buscarPorId.id()).isNotNull();
        assertThat(buscarPorId.idPreConselho()).isNotNull();
    }

    @Test
    void deveAtualizarPreConselhoPedagogicoComSucesso() {
        PreConselhoPedagogicoRequestDTO requestDTO = new PreConselhoPedagogicoRequestDTO(2L, "Compreensão", "Avisos", "Melhorar tempo de avisos.");
        PreConselhoPedagogicoResponseDTO responseDTO = preConselhoPedagogicoService.criarPreConselhoPedagogico(requestDTO);
        PreConselhoPedagogicoRequestDTO atualizar = new PreConselhoPedagogicoRequestDTO(2L, "Calma", "Tempo menor de espera", "Ser rigida ate demais");
        PreConselhoPedagogicoResponseDTO atualizado = preConselhoPedagogicoService.atualizarPreConselhoPedagogico(responseDTO.id(), atualizar);

        assertThat(atualizado).isNotNull();
        assertThat(atualizado.idPreConselho()).isNotNull();
    }

    @Test
    void deveDeletarPreConselhoPedagogicoComSucesso() {
        PreConselhoPedagogicoRequestDTO requestDTO = new PreConselhoPedagogicoRequestDTO(1L,"Limpo", "Ar", "Melhorar ar");
        PreConselhoPedagogicoResponseDTO responseDTO = preConselhoPedagogicoService.criarPreConselhoPedagogico(requestDTO);
        preConselhoPedagogicoService.deletarPreConselhoPedagogico(responseDTO.id());

        assertThat(preConselhoPedagogicoRepository.findById(responseDTO.id())).isEmpty();
    }
}