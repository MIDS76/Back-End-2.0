package com.conselho.api.testeIntegracao.entity;

import com.conselho.api.dto.request.entity.ProfessorRequestDTO;
import com.conselho.api.dto.response.entity.ProfessorResponseDTO;
import com.conselho.api.model.entity.Professor;
import com.conselho.api.model.usuario.UsuarioRole;
import com.conselho.api.repository.entity.ProfessorRepository;
import com.conselho.api.service.entity.ProfessorService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProfessorTesteIntegracao {

    private final ProfessorService professorService;
    private final ProfessorRepository professorRepository;

    public ProfessorTesteIntegracao (
            ProfessorService professorService,
            ProfessorRepository professorRepository
    ) {
        this.professorService = professorService;
        this.professorRepository = professorRepository;
    }
    private Professor professor;

    @BeforeEach
    void setup() {
        professor = new Professor();
        professor.setNome("Valentim");
        professor.setEmail("vava@gmail.com");
        professor.setSenha("primeiroAcessoProf");
        professor.setRole(UsuarioRole.PROFESSOR);

        professorRepository.save(professor);
    }

    @Test
    void deveListarTodosProfessoresComSucesso() {
        var professor = professorService.listarProfessores();

        assertThat(professor).isNotEmpty();
    }

    @Test
    void deveBuscarProfessorPorIdComSucesso() {
        ProfessorResponseDTO response = professorService.buscarProfessorPorId(professor.getId());

        assertThat(response).isNotNull();
        assertThat(response.nome()).isEqualTo("Valentim");
        assertThat(response.email()).isEqualTo("vava@gmail.com");
    }

    @Test
    void deveAtualizarProfessorComSucesso() {
        ProfessorRequestDTO request = new ProfessorRequestDTO("Ricardo", "ricardo@gmail.com");
        professorService.atualizarProfessor(professor.getId(), request);

        Professor updatedProfessor = professorRepository.findById(professor.getId()).orElseThrow();
        assertThat(updatedProfessor.getNome()).isEqualTo("Ricardo");
        assertThat(updatedProfessor.getEmail()).isEqualTo("ricardo@gmail.com");
    }

    @Test
    void deveDeletarProfessorComSucesso() {
        Long id = professor.getId();
        professorService.deletarProfessor(id);

        assertThat(professorRepository.findById(id)).isEmpty();
    }

    @Test
    void naoDeveAtualizarProfessorComEmailDuplicado() {
        Professor professor2 = new Professor();
        professor2.setNome("Ricardo");
        professor2.setEmail("ricardo@gmail.com");
        professor2.setSenha("bobina123");
        professor2.setRole(UsuarioRole.PROFESSOR);
        professorRepository.save(professor2);
        ProfessorRequestDTO request = new ProfessorRequestDTO("Bruno", "ricardo@gmail.com");

        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () ->
                professorService.atualizarProfessor(professor.getId(), request)
        );
    }

}
