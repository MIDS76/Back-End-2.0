package com.conselho.api.testeIntegracao;

import com.conselho.api.dto.request.AlunoRequestDTO;
import com.conselho.api.dto.request.ProfessorRequestDTO;
import static org.assertj.core.api.Assertions.assertThat;
import com.conselho.api.model.Aluno;
import com.conselho.api.model.Professor;
import com.conselho.api.model.Supervisor;
import com.conselho.api.repository.*;
import com.conselho.api.service.CadastroService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class CadastroTesteIntegracao {

    @Autowired
    private CadastroService cadastroService;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private PedagogicoRepository pedagogicoRepository;
    @Autowired
    private SupervisorRepository supervisorRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void limparBanco() {
        usuarioRepository.deleteAll();
        alunoRepository.deleteAll();
        professorRepository.deleteAll();
        pedagogicoRepository.deleteAll();
        supervisorRepository.deleteAll();
    }

    // Teste para Aluno
    @Test
    void deveCadastrarAlunoComSucesso() {
        AlunoRequestDTO request = new AlunoRequestDTO("Guilherme", "guilherme@email.com", "guigui123", true);

        cadastroService.cadastrarAluno(request);

        Aluno alunoSalvo = alunoRepository.findAll().get(0);
        Long id = alunoSalvo.getId();

        Aluno alunoBanco = alunoRepository.findById(id).orElseThrow();
        assertThat(alunoBanco.getNome()).isEqualTo("João");
        assertThat(alunoBanco.getEmail()).isEqualTo("joao@email.com");
    }

    // Teste para Professor
    @Test
    void deveCadastrarProfessorComSucesso() {
        ProfessorRequestDTO request = new ProfessorRequestDTO("Ricardo", "ricardo@gmail.com", "ricardo123");
        cadastroService.cadastroProfessor(request);

        Professor professor = professorRepository.findAll().get(0);
        Long id = professor.getId();

        Professor prof = professorRepository.findById(id).orElseThrow();
        assertThat(prof.getNome()).isEqualTo("Ricardo");
        assertThat(prof.getEmail()).isEqualTo("ricardo@gmail.com");
    }

    // Teste para Supervisor
    @Test
    void deveCadastrarSupervisorComSucesso() {
        Supervisor salvo = supervisorRepository.save(new Supervisor("Andrew", "andrew@email.com", "aw123"));
        Long id = salvo.getId();

        Supervisor supervisor = supervisorRepository.findById(id).orElseThrow();
        assertThat(supervisor.getNome()).isEqualTo("Andrew");
        assertThat(supervisor.getEmail()).isEqualTo("andrew@email.com");
    }
}


