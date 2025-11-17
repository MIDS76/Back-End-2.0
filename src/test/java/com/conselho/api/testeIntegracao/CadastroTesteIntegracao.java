//package com.conselho.api.testeIntegracao;
//
//
//import static org.assertj.core.api.Assertions.assertThat;
//import com.conselho.api.dto.request.entity.AlunoRequestDTO;
//import com.conselho.api.dto.request.entity.ProfessorRequestDTO;
//import com.conselho.api.model.entity.Aluno;
//import com.conselho.api.model.entity.Professor;
//import com.conselho.api.model.entity.Supervisor;
//import com.conselho.api.repository.ConselhoRepository;
//import com.conselho.api.repository.entity.*;
//import com.conselho.api.service.CadastroService;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestConstructor;
//
//@SpringBootTest
//@Transactional
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
//public class CadastroTesteIntegracao {
//
//    private final CadastroService cadastroService;
//    private final AlunoRepository alunoRepository;
//    private final ProfessorRepository professorRepository;
//    private final PedagogicoRepository pedagogicoRepository;
//    private final SupervisorRepository supervisorRepository;
//    private final UsuarioRepository usuarioRepository;
//    private final ConselhoRepository conselhoRepository;
//
//    public CadastroTesteIntegracao (
//        CadastroService cadastroService,
//        AlunoRepository alunoRepository,
//        ProfessorRepository professorRepository,
//        PedagogicoRepository pedagogicoRepository,
//        SupervisorRepository supervisorRepository,
//        UsuarioRepository usuarioRepository,
//        ConselhoRepository conselhoRepository
//    ){
//        this.cadastroService = cadastroService;
//        this.alunoRepository = alunoRepository;
//        this.professorRepository = professorRepository;
//        this.pedagogicoRepository = pedagogicoRepository;
//        this.supervisorRepository = supervisorRepository;
//        this.usuarioRepository = usuarioRepository;
//        this.conselhoRepository = conselhoRepository;
//    }
//
//    @BeforeEach
//    void limparBanco() {
//        if (conselhoRepository != null) {
//            conselhoRepository.deleteAll();
//        }
//        alunoRepository.deleteAll();
//        professorRepository.deleteAll();
//        supervisorRepository.deleteAll();
//        pedagogicoRepository.deleteAll();
//
//        usuarioRepository.deleteAll();
//    }
//
//    @Test
//    void deveCadastrarAlunoComSucesso() {
//        AlunoRequestDTO request = new AlunoRequestDTO("1212", "Amanda", "amanda@email.com");
//
//        cadastroService.cadastrarAluno(request);
//
//        Aluno alunoSalvo = alunoRepository.findAll().get(0);
//        Long id = alunoSalvo.getId();
//
//        Aluno alunoBanco = alunoRepository.findById(id).orElseThrow();
//        assertThat(alunoBanco.getNome()).isEqualTo("Amanda");
//        assertThat(alunoBanco.getEmail()).isEqualTo("amanda@email.com");
//    }
//
//    @Test
//    void deveCadastrarProfessorComSucesso() {
//        ProfessorRequestDTO request = new ProfessorRequestDTO("Ricardo", "ricardo@gmail.com");
//        cadastroService.cadastroProfessor(request);
//
//        Professor professor = professorRepository.findAll().get(0);
//        Long id = professor.getId();
//
//        Professor prof = professorRepository.findById(id).orElseThrow();
//        assertThat(prof.getNome()).isEqualTo("Ricardo");
//        assertThat(prof.getEmail()).isEqualTo("ricardo@gmail.com");
//    }
//
//    @Test
//    void deveCadastrarSupervisorComSucesso() {
//        Supervisor salvo = supervisorRepository.save(new Supervisor("Andrew", "andrew@email.com", "aw123"));
//        Long id = salvo.getId();
//
//        Supervisor supervisor = supervisorRepository.findById(id).orElseThrow();
//        assertThat(supervisor.getNome()).isEqualTo("Andrew");
//        assertThat(supervisor.getEmail()).isEqualTo("andrew@email.com");
//    }
//}
//
//
