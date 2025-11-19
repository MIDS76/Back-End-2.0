package com.conselho.api.testeIntegracao.preConselho;

import com.conselho.api.dto.request.preConselho.PreConselhoAmbienteEnsinoRequestDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoAmbienteEnsinoResponseDTO;
import com.conselho.api.model.preConselho.PreConselho;
import com.conselho.api.model.preConselho.PreConselhoAmbienteEnsino;
import com.conselho.api.repository.preConselho.PreConselhoAmbienteEnsinoRepository;
import com.conselho.api.service.preConselho.PreConselhoAmbienteEnsinoService;
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
public class PreConselhoAmbienteEnsinoTesteIntegracao {
    private final PreConselhoAmbienteEnsinoService preConselhoAmbienteEnsinoService;
    private final PreConselhoAmbienteEnsinoRepository preConselhoAmbienteEnsinoRepository;

    public PreConselhoAmbienteEnsinoTesteIntegracao (
        PreConselhoAmbienteEnsinoService preConselhoAmbienteEnsinoService,
        PreConselhoAmbienteEnsinoRepository preConselhoAmbienteEnsinoRepository
    ){
        this.preConselhoAmbienteEnsinoService = preConselhoAmbienteEnsinoService;
        this.preConselhoAmbienteEnsinoRepository = preConselhoAmbienteEnsinoRepository;
    }

    @BeforeEach
    void setup() {
        PreConselhoAmbienteEnsino preConselhoAmbienteEnsino =  new PreConselhoAmbienteEnsino();

        preConselhoAmbienteEnsino.setPreConselho(new PreConselho());
        preConselhoAmbienteEnsino.setPontosPositivos("Limpo");
        preConselhoAmbienteEnsino.setPontosMelhoria("Ar");
        preConselhoAmbienteEnsino.setSugestoes("Arrumar cabeamento");

        preConselhoAmbienteEnsinoRepository.save(preConselhoAmbienteEnsino);
    }

    @Test
    void deveCriarPreConselhoAmbienteEnsinoComSucesso() {
        PreConselhoAmbienteEnsinoRequestDTO requestDTO = new PreConselhoAmbienteEnsinoRequestDTO(1L,"Limpeza", "Ar", "Melhorar Ar");
        PreConselhoAmbienteEnsinoResponseDTO responseDTO = preConselhoAmbienteEnsinoService.criarPreConselhoAmbienteEnsino(requestDTO);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.id()).isNotNull();
        assertThat(responseDTO.idPreConselho()).isNotNull();
        assertThat(responseDTO.pontosPositivos());
        assertThat(responseDTO.pontosMelhoria());
        assertThat(responseDTO.sugestoes());
    }

    @Test
    void deveListarPreConselhoAmbienteEnsinoComSucesso() {
        PreConselhoAmbienteEnsinoRequestDTO requestDTO = new PreConselhoAmbienteEnsinoRequestDTO(1L, "Limpeza", "Avisos","Melhorar Avisos");
        preConselhoAmbienteEnsinoService.criarPreConselhoAmbienteEnsino(requestDTO);
        List<PreConselhoAmbienteEnsinoResponseDTO> preConselhoAmbienteEnsino = preConselhoAmbienteEnsinoService.listarTodos();

        assertThat(preConselhoAmbienteEnsino).isNotNull();
    }

    @Test
    void deveBuscarPreConselhoAmbienteEnsinoPorIdComSucesso() {
        PreConselhoAmbienteEnsinoRequestDTO requestDTO = new PreConselhoAmbienteEnsinoRequestDTO(1L, "Limpo", "Ar", "Arrumar ar");
        PreConselhoAmbienteEnsinoResponseDTO responseDTO = preConselhoAmbienteEnsinoService.criarPreConselhoAmbienteEnsino(requestDTO);
        PreConselhoAmbienteEnsinoResponseDTO buscarPorId = preConselhoAmbienteEnsinoService.buscarPorId(responseDTO.id());

        assertThat(buscarPorId).isNotNull();
        assertThat(buscarPorId.id()).isNotNull();
        assertThat(buscarPorId.idPreConselho()).isNotNull();
    }

    @Test
    void deveAtualizarPreConselhoAmbienteComSucesso() {
        PreConselhoAmbienteEnsinoRequestDTO requestDTO = new PreConselhoAmbienteEnsinoRequestDTO(1L, "Limpo", "Ar", "Arrumar ar");
        PreConselhoAmbienteEnsinoResponseDTO responseDTO = preConselhoAmbienteEnsinoService.criarPreConselhoAmbienteEnsino(requestDTO);
        PreConselhoAmbienteEnsinoRequestDTO atualizar = new PreConselhoAmbienteEnsinoRequestDTO(1L,"Limpeza", "Ar-condicionado", "Arrumar cabeamento");
        PreConselhoAmbienteEnsinoResponseDTO atualizado = preConselhoAmbienteEnsinoService.atualizarPreConselhoAmbienteEnsino(responseDTO.id(), atualizar);

        assertThat(atualizado).isNotNull();
        assertThat(atualizado.idPreConselho()).isNotNull();
    }

    @Test
    void deveDeletarPreConselhoAmbienteComSucesso() {
        PreConselhoAmbienteEnsinoRequestDTO requestDTO = new PreConselhoAmbienteEnsinoRequestDTO(1L,"Limpo", "Ar", "Melhorar ar");
        PreConselhoAmbienteEnsinoResponseDTO responseDTO = preConselhoAmbienteEnsinoService.criarPreConselhoAmbienteEnsino(requestDTO);

        preConselhoAmbienteEnsinoService.deletarPreConselhoAmbienteEnsino(responseDTO.id());

        assertThat(preConselhoAmbienteEnsinoRepository.findById(responseDTO.id())).isEmpty();
    }
}
