package com.conselho.api.serviceTesteUnitario;
import com.conselho.api.dto.request.SupervisorRequestDTO;
import com.conselho.api.dto.request.entity.AlunoRequestDTO;
import com.conselho.api.dto.request.entity.PedagogicoRequestDTO;
import com.conselho.api.dto.request.entity.ProfessorRequestDTO;
import com.conselho.api.model.entity.Aluno;
import com.conselho.api.model.entity.Pedagogico;
import com.conselho.api.model.entity.Professor;
import com.conselho.api.model.entity.Supervisor;
import com.conselho.api.repository.entity.*;
import com.conselho.api.service.CadastroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CadastroServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private AlunoRepository alunoRepository;
    @Mock
    private ProfessorRepository professorRepository;
    @Mock
    private PedagogicoRepository pedagogicoRepository;
    @Mock
    private SupervisorRepository supervisorRepository;

    @InjectMocks
    private CadastroService cadastroService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriptografarSenha() {
        String senha = "123456";
        String resultado = cadastroService.criptografarSenha(senha);

        assertNotNull(resultado);
        assertNotEquals(senha, resultado);
        assertTrue(new BCryptPasswordEncoder().matches(senha, resultado));
    }

    @Test
    void deveCadastrarAluno_ComSucesso() {
        AlunoRequestDTO request = new AlunoRequestDTO("Julia", "julia@email.com", "123",  false);

        when(alunoRepository.findByEmail("julia@email.com"))
                .thenReturn(null);

        cadastroService.cadastrarAluno(request);

        verify(usuarioRepository, times(1))
                .save(any(Aluno.class));
        verify(alunoRepository, times(1))
                .save(any(Aluno.class));
    }

    @Test
    void deveLancarErro_QuandoEmailAlunoJaExiste() {
        AlunoRequestDTO request = new AlunoRequestDTO("Julia", "julia@email.com", "123");
        when(alunoRepository.findByEmail("julia@email.com"))
                .thenReturn(new Aluno());

        assertThrows(RuntimeException.class, () -> cadastroService.cadastrarAluno(request));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void deveCadastrarPedagogico_ComSucesso() {
        PedagogicoRequestDTO request = new PedagogicoRequestDTO("Maria", "maria@email.com", "123");

        when(pedagogicoRepository.findByEmail("maria@email.com")).thenReturn(null);

        cadastroService.cadastroPedagogico(request);

        verify(usuarioRepository, times(1)).save(any(Pedagogico.class));
        verify(pedagogicoRepository, times(1)).save(any(Pedagogico.class));
    }
    @Test
    void deveLancarErro_QuandoEmailPedagogicoJaExiste() {
        PedagogicoRequestDTO request = new PedagogicoRequestDTO("Maria", "maria@email.com", "123");
        when(pedagogicoRepository.findByEmail("maria@email.com")).thenReturn(new Pedagogico());

        assertThrows(RuntimeException.class, () -> cadastroService.cadastroPedagogico(request));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void deveCadastrarProfessor_ComSucesso() {
        ProfessorRequestDTO request = new ProfessorRequestDTO("Carlos", "carlos@email.com", "123");

        when(professorRepository.findByEmail("carlos@email.com")).thenReturn(null);

        cadastroService.cadastroProfessor(request);

        verify(usuarioRepository, times(1)).save(any(Professor.class));
        verify(professorRepository, times(1)).save(any(Professor.class));
    }

    @Test
    void deveLancarErro_QuandoEmailProfessorJaExiste() {
        ProfessorRequestDTO request = new ProfessorRequestDTO("Carlos", "carlos@email.com", "123");
        when(professorRepository.findByEmail("carlos@email.com")).thenReturn(new Professor());

        assertThrows(RuntimeException.class, () -> cadastroService.cadastroProfessor(request));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void deveCadastrarSupervisor_ComSucesso() {
        SupervisorRequestDTO request = new SupervisorRequestDTO("Ana", "ana@email.com", "123");

        when(supervisorRepository.findByEmail("ana@email.com")).thenReturn(null);

        cadastroService.cadastroSupervisor(request);

        verify(usuarioRepository, times(1)).save(any(Supervisor.class));
        verify(supervisorRepository, times(1)).save(any(Supervisor.class));
    }

    @Test
    void deveLancarErro_QuandoEmailSupervisorJaExiste() {
        SupervisorRequestDTO request = new SupervisorRequestDTO("Ana", "ana@email.com", "123");
        when(supervisorRepository.findByEmail("ana@email.com")).thenReturn(new Supervisor());

        assertThrows(RuntimeException.class, () -> cadastroService.cadastroSupervisor(request));
        verify(usuarioRepository, never()).save(any());
    }

}