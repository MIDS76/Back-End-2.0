package com.conselho.api.testeIntegracao.entity;

import com.conselho.api.dto.request.entity.AlunoRequestDTO;
import static org.assertj.core.api.Assertions.assertThat;
import com.conselho.api.dto.response.entity.AlunoResponseDTO;
import com.conselho.api.model.entity.Aluno;
import com.conselho.api.model.usuario.UsuarioRole;
import com.conselho.api.repository.entity.AlunoRepository;
import com.conselho.api.service.entity.AlunoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

@SpringBootTest
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class AlunoTesteIntegracao {

    private final AlunoService alunoService;
    private final AlunoRepository alunoRepository;

    public AlunoTesteIntegracao (
            AlunoService alunoService,
            AlunoRepository alunoRepository
    ){
        this.alunoService = alunoService;
        this.alunoRepository = alunoRepository;
    }
    private Aluno aluno;

    @BeforeEach
    void setup() {
        aluno = new Aluno();
        aluno.setMatricula("1457");
        aluno.setNome("Vitor Eduardo");
        aluno.setEmail("vitinhoorei@gmail.com");
        aluno.setSenha("rei123");
        aluno.setRole(UsuarioRole.ALUNO);
        aluno.setRepresentante(false);

        alunoRepository.save(aluno);
    }

    @Test
    void deveListarAlunosComSucesso() {
        var aluno = alunoService.listarAlunos();

        assertThat(aluno).isNotEmpty();
    }

    @Test
    void deveBuscarAlunoPorId() {
        AlunoResponseDTO dto = alunoService.buscarAlunoPorId(aluno.getId());

        assertThat(dto).isNotNull();
        assertThat(dto.nome()).isEqualTo("Vitor Eduardo");
        assertThat(dto.email()).isEqualTo("vitinhoorei@gmail.com");
    }

    @Test
    void deveAtualizarAlunoComSucesso() {
        AlunoRequestDTO request = new AlunoRequestDTO("1457", "Duda", "duda@gmail.com");

        alunoService.atualizarAluno(aluno.getId(), request);

        Aluno atualizado = alunoRepository.findById(aluno.getId()).orElseThrow();
        assertThat(atualizado.getNome()).isEqualTo("Duda");
        assertThat(atualizado.getEmail()).isEqualTo("duda@gmail.com");
    }

    @Test
    void deveDeletarAluno() {
        AlunoResponseDTO deletado = alunoService.deletarAluno(aluno.getId());

        assertThat(deletado.email()).isEqualTo("vitinhoorei@gmail.com");
        assertThat(alunoRepository.findById(aluno.getId())).isEmpty();
    }

}