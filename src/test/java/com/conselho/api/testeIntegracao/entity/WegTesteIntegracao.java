package com.conselho.api.testeIntegracao.entity;

import com.conselho.api.dto.request.entity.WegRequestDTO;
import com.conselho.api.dto.response.entity.WegResponseDTO;
import com.conselho.api.model.entity.Weg;
import com.conselho.api.model.usuario.UsuarioRole;
import com.conselho.api.repository.entity.WegRepository;
import com.conselho.api.service.entity.WegService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class WegTesteIntegracao {

    private final WegService wegService;
    private final WegRepository wegRepository;

    public WegTesteIntegracao (
            WegService wegService,
            WegRepository wegRepository
    ){
        this.wegService = wegService;
        this.wegRepository = wegRepository;
    }

    private Weg weg;

    @BeforeEach
    void setup() {
        weg = new Weg();
        weg.setNome("Nathalia");
        weg.setEmail("nathalia@gmail.com");
        weg.setSenha("nati123");
        weg.setRole(UsuarioRole.WEG);

        wegRepository.save(weg);
    }

    @Test
    void deveBuscarWegPorId() {
        WegResponseDTO dto = wegService.buscarPorId(weg.getId());

        assertThat(dto).isNotNull();
        assertThat(dto.nome()).isEqualTo("Nathalia");
        assertThat(dto.email()).isEqualTo("nathalia@gmail.com");
    }

    @Test
    void deveAtualizarWegComSucesso() {
        WegRequestDTO requestDTO = new WegRequestDTO("Natalia", "natalia@gmail.com", "nati123");
        WegResponseDTO updatedDto = wegService.update(weg.getId(), requestDTO);

        assertThat(updatedDto).isNotNull();
        assertThat(updatedDto.nome()).isEqualTo("Natalia");
        assertThat(updatedDto.email()).isEqualTo("natalia@gmail.com");

        Weg atualizadoWeg = wegRepository.findById(weg.getId()).orElseThrow();
        assertThat(atualizadoWeg.getNome()).isEqualTo("Natalia");
        assertThat(atualizadoWeg.getEmail()).isEqualTo("natalia@gmail.com");
    }

    @Test
    void deveDeletarWeg() {
        wegService.delete(weg.getId());

        assertThat(wegRepository.findById(weg.getId())).isEmpty();
    }
}
