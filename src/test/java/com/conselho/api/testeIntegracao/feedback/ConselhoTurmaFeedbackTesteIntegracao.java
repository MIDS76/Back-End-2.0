package com.conselho.api.testeIntegracao.feedback;

import com.conselho.api.dto.request.feedback.ConselhoTurmaFeedbackRequestDTO;
import com.conselho.api.dto.response.feedback.ConselhoTurmaFeedbackResponseDTO;
import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.model.conselho.EtapasConselho;
import com.conselho.api.model.entity.Aluno;
import com.conselho.api.model.entity.Pedagogico;
import com.conselho.api.model.entity.Turma;
import com.conselho.api.model.usuario.UsuarioRole;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.TurmaRepository;
import com.conselho.api.repository.entity.AlunoRepository;
import com.conselho.api.repository.entity.PedagogicoRepository;
import com.conselho.api.repository.feedback.ConselhoTurmaFeedbackRepository;
import com.conselho.api.service.feedback.ConselhoTurmaFeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ConselhoTurmaFeedbackTesteIntegracao {

    private final ConselhoTurmaFeedbackService service;
    private final ConselhoTurmaFeedbackRepository repository;
    private final ConselhoRepository conselhoRepository;
    private final PedagogicoRepository pedagogicoRepository;
    private final TurmaRepository turmaRepository;
    private final AlunoRepository alunoRepository;

    private Long conselhoId;
    private Long pedagogicoId;

    public ConselhoTurmaFeedbackTesteIntegracao(
            ConselhoTurmaFeedbackService service,
            ConselhoTurmaFeedbackRepository repository,
            ConselhoRepository conselhoRepository,
            PedagogicoRepository pedagogicoRepository,
            TurmaRepository turmaRepository,
            AlunoRepository alunoRepository
    ) {
        this.service = service;
        this.repository = repository;
        this.conselhoRepository = conselhoRepository;
        this.pedagogicoRepository = pedagogicoRepository;
        this.turmaRepository = turmaRepository;
        this.alunoRepository = alunoRepository;
    }

    @BeforeEach
    void setup() {
        Turma t = new Turma();
        t.setNome("Turma A");
        t.setCurso("Informática");
        t.setAtivo(true);
        t.setDataInicio(LocalDate.now());
        t.setDataFim(LocalDate.now().plusDays(30));

        t = turmaRepository.save(t);

        Aluno rep = new Aluno();
        rep.setNome("Maria");
        rep.setEmail("maria@gmail.com");
        rep.setSenha("1313");
        rep.setMatricula("121441412");
        rep.setRole(UsuarioRole.ALUNO);
        rep.setRepresentante(true);

        rep = alunoRepository.save(rep);

        Pedagogico pedagogico = new Pedagogico();
        pedagogico.setNome("Joao");
        pedagogico.setEmail("joao@gmail.com");
        pedagogico.setSenha("primeiroAcesso");
        pedagogico.setRole(UsuarioRole.PEDAGOGICO);

        pedagogico = pedagogicoRepository.save(pedagogico);
        this.pedagogicoId = pedagogico.getId();

        // Criar Conselho válido
        Conselho conselho = new Conselho();
        conselho.setDataInicio(LocalDate.now());
        conselho.setDataFim(LocalDate.now().plusDays(1));
        conselho.setEtapas(EtapasConselho.CONSELHO);
        conselho.setPedagogico(pedagogico);
        conselho.setTurma(t);
        conselho.setRepresentante1(rep);

        conselho = conselhoRepository.save(conselho);
        this.conselhoId = conselho.getId();
    }

    @Test
    void deveCriarEListarFeedback() {
        ConselhoTurmaFeedbackRequestDTO request = new ConselhoTurmaFeedbackRequestDTO(
                conselhoId,
                pedagogicoId,
                "Participativo",
                "Falta de foco",
                "Melhorar atenção"
        );

        ConselhoTurmaFeedbackResponseDTO criado = service.create(request);
        assertThat(criado).isNotNull();
        assertThat(criado.id()).isNotNull();

        ConselhoTurmaFeedbackResponseDTO buscado = service.buscarPorId(criado.id());
        assertThat(buscado.pontosPositivos()).isEqualTo("Participativo");

        List<ConselhoTurmaFeedbackResponseDTO> lista = service.buscarTodos();
        assertThat(lista.size()).isGreaterThan(0);
    }

    @Test
    void deveAtualizarFeedbackComSucesso() {
        ConselhoTurmaFeedbackRequestDTO request = new ConselhoTurmaFeedbackRequestDTO(
                conselhoId,
                pedagogicoId,
                "Cooperativos",
                "Dispersão",
                "Focar mais"
        );

        ConselhoTurmaFeedbackResponseDTO criado = service.create(request);

        ConselhoTurmaFeedbackRequestDTO atualizadoReq = new ConselhoTurmaFeedbackRequestDTO(
                conselhoId,
                pedagogicoId,
                "Comunicativos",
                "Conversas paralelas",
                "Melhorar atenção"
        );

        ConselhoTurmaFeedbackResponseDTO atualizado = service.update(criado.id(), atualizadoReq);

        assertThat(atualizado.pontosPositivos()).isEqualTo("Comunicativos");
    }

    @Test
    void deveDeletarComSucesso() {
        ConselhoTurmaFeedbackRequestDTO request = new ConselhoTurmaFeedbackRequestDTO(
                conselhoId,
                pedagogicoId,
                "Boa postura",
                "Indisciplina",
                "Melhorar respeito"
        );

        ConselhoTurmaFeedbackResponseDTO criado = service.create(request);

        service.delete(criado.id());

        assertThat(repository.findById(criado.id())).isEmpty();
    }
}
