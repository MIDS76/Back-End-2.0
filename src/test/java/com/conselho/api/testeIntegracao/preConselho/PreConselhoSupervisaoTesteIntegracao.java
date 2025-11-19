package com.conselho.api.testeIntegracao.preConselho;

import com.conselho.api.dto.request.preConselho.PreConselhoSupervisaoRequestDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoSupervisaoResponseDTO;
import com.conselho.api.model.preConselho.PreConselho;
import com.conselho.api.model.preConselho.PreConselhoSupervisao;
import com.conselho.api.repository.preConselho.PreConselhoSupervisaoRepository;
import com.conselho.api.service.preConselho.PreConselhoSupervisaoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class PreConselhoSupervisaoTesteIntegracao {
    private final PreConselhoSupervisaoService preConselhoSupervisaoService;
    private final PreConselhoSupervisaoRepository preConselhoSupervisaoRepository;

    public PreConselhoSupervisaoTesteIntegracao(
        PreConselhoSupervisaoService preConselhoSupervisaoService,
        PreConselhoSupervisaoRepository preConselhoSupervisaoRepository
    ){
        this.preConselhoSupervisaoService = preConselhoSupervisaoService;
        this.preConselhoSupervisaoRepository = preConselhoSupervisaoRepository;
    }

    @BeforeEach
    void setup() {
        PreConselhoSupervisao preConselhoSupervisao = new PreConselhoSupervisao();

        preConselhoSupervisao.setPreConselho(new PreConselho());
        preConselhoSupervisao.setPontosPositivos("Atenciosos");
        preConselhoSupervisao.setPontosMelhoria("Conversas");
        preConselhoSupervisao.setSugestoes("Marcas conversas");

        preConselhoSupervisaoRepository.save(preConselhoSupervisao);
    }

    @Test
    void deveCriarPreConselhoSupervisao() {
        PreConselhoSupervisaoRequestDTO requestDTO = new PreConselhoSupervisaoRequestDTO(1L, "Atenciosos", "Conversas", "Marcas conversas");
        PreConselhoSupervisaoResponseDTO responseDTO = preConselhoSupervisaoService.criarPreConselhoSupervisao(requestDTO);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.id()).isNotNull();
        assertThat(responseDTO.idPreConselho()).isNotNull();
        assertThat(responseDTO.pontosPostivos());
        assertThat(responseDTO.pontosMelhoria());
        assertThat(responseDTO.sugestoes());
    }

    @Test
    void deveListarPreConselhoSupervisaoComSucesso() {
        PreConselhoSupervisaoRequestDTO requestDTO = new PreConselhoSupervisaoRequestDTO(1L, "Atenção", "Conversas", "Melhorar conversas");
        preConselhoSupervisaoService.criarPreConselhoSupervisao(requestDTO);
        List<PreConselhoSupervisaoResponseDTO> preConselhoSupervisao = preConselhoSupervisaoService.listarTodos();

        assertThat(preConselhoSupervisao).isNotNull();
    }

    @Test
    void deveBuscarPreConselhoSupervisaoPorIdComSucesso() {
        PreConselhoSupervisaoRequestDTO requestDTO = new PreConselhoSupervisaoRequestDTO(1L, "Compreensao", "Avisos", "Melhorar tempo de avisos");
        PreConselhoSupervisaoResponseDTO responseDTO = preConselhoSupervisaoService.criarPreConselhoSupervisao(requestDTO);
        PreConselhoSupervisaoResponseDTO buscarPorId = preConselhoSupervisaoService.buscarPorId(responseDTO.id());

        assertThat(buscarPorId).isNotNull();
        assertThat(buscarPorId.id()).isNotNull();
        assertThat(buscarPorId.idPreConselho()).isNotNull();
    }

    @Test
    void deveAtualizarPreConselhoSupervisaoComSucesso() {
        PreConselhoSupervisaoRequestDTO requestDTO = new PreConselhoSupervisaoRequestDTO(1L, "Compreensão", "Avisos", "Melhorar tempo de avisos.");
        PreConselhoSupervisaoResponseDTO responseDTO = preConselhoSupervisaoService.criarPreConselhoSupervisao(requestDTO);
        PreConselhoSupervisaoRequestDTO atualizar = new PreConselhoSupervisaoRequestDTO(1L, "Calma", "Tempo menor de espera", "Ser rigida ate demais");
        PreConselhoSupervisaoResponseDTO atualizado = preConselhoSupervisaoService.atualizarPreConselhoSupervisao(responseDTO.id(), atualizar);

        assertThat(atualizado).isNotNull();
        assertThat(atualizado.idPreConselho()).isNotNull();
    }

    @Test
    void deveDeletarPreConselhoSupervisaoComSucesso() {
        PreConselhoSupervisaoRequestDTO requestDTO = new PreConselhoSupervisaoRequestDTO(1L,"Limpo", "Ar", "Melhorar ar");
        PreConselhoSupervisaoResponseDTO responseDTO = preConselhoSupervisaoService.criarPreConselhoSupervisao(requestDTO);
        preConselhoSupervisaoService.deletarPreConselhoSupervisao(responseDTO.id());

        assertThat(preConselhoSupervisaoRepository.findById(responseDTO.id())).isEmpty();
    }
}
