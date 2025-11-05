package com.conselho.api.testeIntegracao;

import com.conselho.api.dto.mapper.AlunoMapper;
import com.conselho.api.dto.request.AlunoRequestDTO;
import com.conselho.api.dto.response.AlunoResponseDTO;
import static org.assertj.core.api.Assertions.assertThat;
import com.conselho.api.model.Aluno;
import com.conselho.api.model.usuario.UsuarioRole;
import com.conselho.api.repository.AlunoRepository;
import com.conselho.api.repository.UsuarioRepository;
import com.conselho.api.service.AlunoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
@SpringBootTest
@Transactional
public class AlunoTesteIntegracao {

    @Autowired
    private AlunoService alunoService;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AlunoMapper mapper;

    private Aluno aluno;

    @BeforeEach
    void setup() {
        aluno = new Aluno();
        aluno.setNome("Vitor Eduardo");
        aluno.setEmail("vitinhoorei@email.com");
        aluno.setSenha("rei123");
        aluno.setRole(UsuarioRole.ALUNO);
        aluno.setRepresentante(true);
        alunoRepository.save(aluno);
    }

    @Test
    void deveListarAlunosComSucesso() {
        List<AlunoResponseDTO> alunos = alunoService.listarAlunos();

        assertThat(alunos).isNotEmpty();
        assertThat(alunos.get(0).nome()).isEqualTo("Vitor Eduardo");
    }

    @Test
    void deveBuscarAlunoPorId() {
        AlunoResponseDTO dto = alunoService.buscarAlunoPorId(aluno.getId());

        assertThat(dto).isNotNull();
        assertThat(dto.email()).isEqualTo("vitinhoorei@email.com");
    }

    @Test
    void deveAtualizarAlunoComSucesso() {
        AlunoRequestDTO request = new AlunoRequestDTO("Guilherme Pereira", "pereira@email.com", "he123", false);

        alunoService.atualizarAluno(aluno.getId(), request);

        Aluno atualizado = alunoRepository.findById(aluno.getId()).get();
        assertThat(atualizado.getEmail()).isEqualTo("pereira@email.com");
        assertThat(atualizado.getNome()).isEqualTo("Guilherme Pereira");
    }

    @Test
    void deveDeletarAluno() {
        AlunoResponseDTO deletado = alunoService.deletarAluno(aluno.getId());

        assertThat(deletado.email()).isEqualTo("vitinhoorei@email.com");
        assertThat(alunoRepository.findById(aluno.getId())).isEmpty();
    }

    @Test
    void deveVerificarSeEhRepresentante() {
        boolean resultado = alunoService.isRepresentante(aluno.getId());

        assertThat(resultado).isTrue();
    }

    @Test
    void deveBuscarRepresentante() {
        Aluno representante = alunoService.getRepresentante();

        assertThat(representante).isNotNull();
        assertThat(representante.getNome()).isEqualTo("Vitor Eduardo");
    }
}