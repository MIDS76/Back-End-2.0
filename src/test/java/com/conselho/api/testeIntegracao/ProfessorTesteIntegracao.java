package com.conselho.api.testeIntegracao;

import com.conselho.api.dto.request.ProfessorRequestDTO;
import com.conselho.api.dto.response.ProfessorResponseDTO;
import com.conselho.api.model.Professor;
import com.conselho.api.model.usuario.UsuarioRole;
import com.conselho.api.repository.ProfessorRepository;
import com.conselho.api.repository.UsuarioRepository;
import com.conselho.api.service.ProfessorService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ProfessorTesteIntegracao {
    @Autowired
    private ProfessorService professorService;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Professor professor;

    @BeforeEach
    void setup() {
        professor = new Professor();
        professor.setNome("Valentim");
        professor.setEmail("valentim@gmail.com");
        professor.setSenha("vava123");
        professor.setRole(UsuarioRole.PROFESSOR);
        professorRepository.save(professor);
    }

    @Test
    void deveListarTodosProfessoresComSucesso() {
        List<ProfessorResponseDTO> professores = professorService.listarProfessores();

        assertThat(professores).isNotEmpty();
        assertThat(professores.get(0).nome()).isEqualTo("Valentim");
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
        ProfessorRequestDTO request = new ProfessorRequestDTO("Ricardo", "ricardo@test.com", "riri123");
        professorService.atualizarProfessor(professor.getId(), request);

        Professor updatedProfessor = professorRepository.findById(professor.getId()).orElseThrow();
        assertThat(updatedProfessor.getNome()).isEqualTo("Ricardo");
        assertThat(updatedProfessor.getEmail()).isEqualTo("ricardo@test.com");
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
        professor2.setEmail("ricardo@test.com");
        professor2.setSenha("riri123");
        professor2.setRole(UsuarioRole.PROFESSOR);
        professorRepository.save(professor2);
        ProfessorRequestDTO request = new ProfessorRequestDTO("Bruno", "ricardo@test.com", "1234");

        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () ->
                professorService.atualizarProfessor(professor.getId(), request)
        );
    }

}
