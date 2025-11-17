package com.conselho.api.testeIntegracao;

import com.conselho.api.dto.request.AlunoTurmaRequestDTO;
import com.conselho.api.dto.response.AlunoTurmaResponseDTO;
import com.conselho.api.model.AlunoTurma;
import com.conselho.api.model.entity.Aluno;
import com.conselho.api.model.entity.Turma;
import com.conselho.api.model.usuario.UsuarioRole;
import com.conselho.api.repository.AlunoTurmaRepository;
import com.conselho.api.repository.TurmaRepository;
import com.conselho.api.repository.entity.AlunoRepository;
import com.conselho.api.repository.entity.UsuarioRepository;
import com.conselho.api.service.AlunoTurmaService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class AlunoTurmaTesteIntegracao {

    private final AlunoTurmaService alunoTurmaService;
    private final TurmaRepository turmaRepository;
    private final AlunoRepository alunoRepository;
    private final AlunoTurmaRepository alunoTurmaRepository;
    private final UsuarioRepository usuarioRepository;

    private Turma turma;
    private Aluno alunoTeste1;
    private Aluno alunoTeste2;

    public AlunoTurmaTesteIntegracao(
            AlunoTurmaService alunoTurmaService,
            TurmaRepository turmaRepository,
            AlunoRepository alunoRepository,
            AlunoTurmaRepository alunoTurmaRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.alunoTurmaService = alunoTurmaService;
        this.turmaRepository = turmaRepository;
        this.alunoRepository = alunoRepository;
        this.alunoTurmaRepository = alunoTurmaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @BeforeEach
    void setup() {
        turma = new Turma();
        turma.setNome("MI-76");
        turma.setCurso("Banco de dados");
        turma.setDataInicio(LocalDate.now());
        turma.setDataFim(LocalDate.now().plusDays(30));

        turma = turmaRepository.save(turma);

        alunoTeste1 = new Aluno();
        alunoTeste1.setNome("Hellen");
        alunoTeste1.setMatricula("841");
        alunoTeste1.setEmail("hellen@gmail.com");
        alunoTeste1.setSenha("hellenzita751");
        alunoTeste1.setRole(UsuarioRole.ALUNO);
        alunoTeste1 = alunoRepository.save(alunoTeste1);

        alunoTeste2 = new Aluno();
        alunoTeste2.setNome("Julia");
        alunoTeste2.setMatricula("391");
        alunoTeste2.setEmail("jolia@gmail.com");
        alunoTeste2.setSenha("jolia681");
        alunoTeste2.setRole(UsuarioRole.ALUNO);
        alunoTeste2 = alunoRepository.save(alunoTeste2);
    }


    @Test
    void deveCriarRelacionamentoAlunoTurmaComSucesso() {
        List<Long> idsAlunos = List.of(alunoTeste1.getId(), alunoTeste2.getId());
        AlunoTurmaRequestDTO requestDTO = new AlunoTurmaRequestDTO(turma.getId(), idsAlunos);
        List<AlunoTurmaResponseDTO> responseList = alunoTurmaService.criarAlunoTurma(requestDTO);

        assertThat(responseList).isNotEmpty();
        AlunoTurmaResponseDTO responseDTO = responseList.get(0);

        assertThat(responseDTO.nomeTurma()).isEqualTo(turma.getNome());
        assertThat(responseDTO.nomeAluno()).containsExactlyInAnyOrder(alunoTeste1.getNome(), alunoTeste2.getNome());
        assertThat(responseDTO.ativo()).isTrue();

        List<AlunoTurma> entidadesSalvas = alunoTurmaRepository.findByTurmaId(turma.getId());
        assertThat(entidadesSalvas).hasSize(2);
    }


    @Test
    void deveListarNomesDosAlunosPorIdTurmaComSucesso() {
        AlunoTurma alunoTurma = new AlunoTurma(turma, alunoTeste1);
        AlunoTurma alunoTur = new AlunoTurma(turma, alunoTeste2);
        alunoTurmaRepository.saveAll(List.of(alunoTurma, alunoTur));

        List<String> nomesAlunos = alunoTurmaService.listarAlunosPorId(turma.getId());

        assertThat(nomesAlunos).isNotNull();
        assertThat(nomesAlunos).hasSize(2);
        assertThat(nomesAlunos).containsExactlyInAnyOrder(alunoTeste1.getNome(), alunoTeste2.getNome());
    }
}