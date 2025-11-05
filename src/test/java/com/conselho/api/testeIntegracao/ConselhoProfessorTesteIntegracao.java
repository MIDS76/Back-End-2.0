package com.conselho.api.testeIntegracao;

import com.conselho.api.dto.mapper.ConselhoProfessorMapper;
import com.conselho.api.dto.request.ConselhoProfessorRequest;
import com.conselho.api.dto.response.ConselhoProfessorResponse;
import static org.assertj.core.api.Assertions.assertThat;
import com.conselho.api.model.ConselhoProfessor;
import com.conselho.api.model.Professor;
import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.repository.ConselhoProfessorRespository;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.ProfessorRepository;
import com.conselho.api.service.ConselhoProfessorService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@Transactional
public class ConselhoProfessorTesteIntegracao {
    @Autowired
    private ConselhoProfessorService conselhoProfessorService;
    @Autowired
    private ConselhoProfessorRespository conselhoProfessorRespository;
    @Autowired
    private ConselhoRepository conselhoRepository;
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private ConselhoProfessorMapper mapper;
    private Conselho conselho;
    private Professor professor;

    @BeforeEach
    void setup() {
        conselho = new Conselho();
        conselho.getId();
        conselhoRepository.save(conselho);

        professor = new Professor();
        professor.setNome("Willer teacher");
        professor.setEmail("willer@test.com");
        professor.setSenha("123");
        professorRepository.save(professor);
    }

    @Test
    void deveCriarConselhoProfessorComSucesso() {
        ConselhoProfessorRequest request = new ConselhoProfessorRequest(
                conselho.getId(),
                professor.getId()
        );

        ConselhoProfessorResponse response = conselhoProfessorService.criarConselhoProfessor(request);

        assertThat(response).isNotNull();
        assertThat(response.id()).isNotNull();
        assertThat(response.idProfessor()).isEqualTo(professor.getId());
        assertThat(response.nomeProfessor()).isEqualTo("Willer teacher");
    }

    @Test
    void deveListarTodosConselhosProfessores() {
        ConselhoProfessor conProf = new ConselhoProfessor();
        conProf.setConselho(conselho);
        conProf.setProfessor(professor);
        conselhoProfessorRespository.save(conProf);

        List<ConselhoProfessorResponse> lista = conselhoProfessorService.buscarTodos();

        assertThat(lista).isNotEmpty();
        assertThat(lista.get(0).idProfessor().equals(professor.getId()));
    }

    @Test
    void deveAtualizarConselhoProfessorComSucesso() {
        ConselhoProfessor conselhoProf = new ConselhoProfessor();
        conselhoProf.setConselho(conselho);
        conselhoProf.setProfessor(professor);
        conselhoProfessorRespository.save(conselhoProf);

        ConselhoProfessorRequest request = new ConselhoProfessorRequest(
                conselho.getId(),
                professor.getId()
        );

        ConselhoProfessorResponse response = conselhoProfessorService.update(conselhoProf.getId(), request);

        assertThat(response).isNotNull();
        assertThat(response.nomeProfessor().equals(professor.getNome()));
    }

    @Test
    void deveDeletarConselhoProfessorComSucesso() {
        ConselhoProfessor conselhoProfessor = new ConselhoProfessor();
        conselhoProfessor.setConselho(conselho);
        conselhoProfessor.setProfessor(professor);
        conselhoProfessorRespository.save(conselhoProfessor);
        conselhoProfessorService.deletarConselhoProfessor(conselhoProfessor.getId());

        assertThat(conselhoProfessorRespository.findById(conselhoProfessor.getId())).isEmpty();
    }

    @Test
    void deveBuscarProfessoresPorConselho() {
        ConselhoProfessor conseProfessor = new ConselhoProfessor();
        conseProfessor.setConselho(conselho);
        conseProfessor.setProfessor(professor);
        conselhoProfessorRespository.save(conseProfessor);

        List<?> professores = conselhoProfessorService.buscarProfessoresPorConselho(conselho.getId());
        assertThat(professores).isNotEmpty();
    }
}

