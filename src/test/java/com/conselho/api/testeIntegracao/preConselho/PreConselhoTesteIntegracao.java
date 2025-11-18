//package com.conselho.api.testeIntegracao.preConselho;
//
//import com.conselho.api.dto.request.preConselho.PreConselhoRequestDTO;
//import com.conselho.api.dto.response.preConselho.PreConselhoResponseDTO;
//import com.conselho.api.model.conselho.Conselho;
//import com.conselho.api.model.preConselho.PreConselho;
//import com.conselho.api.repository.preConselho.PreConselhoRepository;
//import com.conselho.api.service.preConselho.PreConselhoService;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestConstructor;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@SpringBootTest
//@Transactional
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
//public class PreConselhoTesteIntegracao {
//
//    private final PreConselhoService preConselhoService;
//    private final PreConselhoRepository preConselhoRepository;
//    private Conselho conselho;
//
//    public PreConselhoTesteIntegracao(
//        PreConselhoService preConselhoService,
//        PreConselhoRepository preConselhoRepository
//    ){
//        this.preConselhoService = preConselhoService;
//        this.preConselhoRepository = preConselhoRepository;
//    }
//
//    @BeforeEach
//    void setup() {
//        PreConselho preConselho = new PreConselho();
//
//        preConselho.setId(1L);
//        preConselho.setConselho(new Conselho());
//
//        preConselhoRepository.save(preConselho);
//    }
//
//    @Test
//    void deveCriarPreConselhoAutomatico() {
//        PreConselhoRequestDTO requestDTO = new PreConselhoRequestDTO(1L);
//        PreConselhoResponseDTO responseDTO = preConselhoService.criarPreConselhoAutomatico(requestDTO);
//
//        assertThat(responseDTO).isNotNull();
//        assertThat(responseDTO.id()).isNotNull();
//    }
//
//    @Test
//    void deveListarPreConselhoComSucesso() {
//        var preConselho = preConselhoService.buscarTodos();
//
//        assertThat(preConselho).isNotNull();
//    }
//
//    @Test
//    void deveBuscarPreConselhoPorIdComSucesso() {
//        PreConselhoRequestDTO requestDTO = new PreConselhoRequestDTO(1L);
//        PreConselhoResponseDTO responseDTO = preConselhoService.criarPreConselhoAutomatico(requestDTO);
//        PreConselhoResponseDTO buscarPorId = preConselhoService.buscarPorId(responseDTO.id());
//
//        assertThat(buscarPorId).isNotNull();
//        assertThat(buscarPorId.id()).isNotNull();
//        assertThat(buscarPorId.idConselho()).isNotNull();
//    }
//
//    @Test
//    void deveAtualizarPreConselhoComSucesso() {
//        PreConselhoRequestDTO requestDTO = new PreConselhoRequestDTO(2L);
//        PreConselhoResponseDTO responseDTO = preConselhoService.criarPreConselhoAutomatico(requestDTO);
//        PreConselhoResponseDTO atualizado = preConselhoService.update(responseDTO.id(), requestDTO);
//
//        assertThat(atualizado).isNotNull();
//        assertThat(atualizado.idConselho()).isNotNull();
//    }
//    @Test
//    void deveDeletarPreConselhoComSucesso() {
//        PreConselhoRequestDTO requestDTO = new PreConselhoRequestDTO(1L);
//        PreConselhoResponseDTO responseDTO = preConselhoService.criarPreConselhoAutomatico(requestDTO);
//        preConselhoService.delete(responseDTO.id());
//
//        assertThat(preConselhoRepository.findById(responseDTO.id())).isEmpty();
//    }
//}
//
//
