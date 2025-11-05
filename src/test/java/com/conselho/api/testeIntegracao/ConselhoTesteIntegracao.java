package com.conselho.api.testeIntegracao;

import com.conselho.api.dto.request.ConselhoRequestDTO;
import com.conselho.api.dto.response.ConselhoResponseDTO;
import static org.assertj.core.api.Assertions.assertThat;
import com.conselho.api.model.Aluno;
import com.conselho.api.model.Pedagogico;
import com.conselho.api.model.Turma;
import com.conselho.api.repository.AlunoRepository;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.PedagogicoRepository;
import com.conselho.api.repository.TurmaRepository;
import com.conselho.api.service.ConselhoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;


@SpringBootTest
@Transactional
public class ConselhoTesteIntegracao {

    @Autowired
    private ConselhoService conselhoService;
    @Autowired
    private ConselhoRepository conselhoRepository;
    @Autowired
    private TurmaRepository turmaRepository;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private PedagogicoRepository pedagogicoRepository;
    private Turma turma;
    private Aluno aluno1, aluno2;
    private Pedagogico pedagogico;

    @BeforeEach
    void setup() {
        turma = new Turma();
        turma.setNome("Turma de Teste");
        turmaRepository.save(turma);

        aluno1 = new Aluno();
        aluno1.setNome("Hellen");
        aluno1.setEmail("hellen@test.com");
        alunoRepository.save(aluno1);

        aluno2 = new Aluno();
        aluno2.setNome("Julia");
        aluno2.setEmail("julia@test.com");
        alunoRepository.save(aluno2);

        pedagogico = new Pedagogico();
        pedagogico.setNome("Pedagógico Teste");
        pedagogicoRepository.save(pedagogico);
    }

    @Test
    void deveCriarConselhoComSucesso() {
        LocalDate dataInicio = LocalDate.now();
        LocalDate dataFim = LocalDate.now().plusMonths(1);
        String etapas = "NÃO_INICIADO";

        ConselhoRequestDTO request = new ConselhoRequestDTO(
                turma.getId(),
                dataInicio,
                dataFim,
                aluno1.getId(),
                aluno2.getId(),
                pedagogico.getId(),
                etapas
        );

        ConselhoResponseDTO response = conselhoService.criarConselho(request);

        assertThat(response).isNotNull();
        assertThat(response.id()).isNotNull();
        assertThat(response.idRepresentante1()).isEqualTo(aluno1.getId());
        assertThat(response.idRepresentante2()).isEqualTo(aluno2.getId());
        assertThat(response.idPedagogico()).isEqualTo(pedagogico.getId());
    }
    @Test
    void deveListarTodosConselhos() {
        // Adicionando os parâmetros obrigatórios
        LocalDate dataInicio = LocalDate.now();  // Hoje
        LocalDate dataFim = LocalDate.now().plusMonths(1); // Um mês a partir de hoje
        String etapas = "Etapa 1";  // Exemplo de etapa

        // Criando o DTO com todos os 7 parâmetros
        ConselhoRequestDTO request = new ConselhoRequestDTO(
                turma.getId(),
                dataInicio,    // dataInicio
                dataFim,       // dataFim
                aluno1.getId(),
                aluno2.getId(),
                pedagogico.getId(),
                etapas          // etapas
        );

        // Criando o conselho
        conselhoService.criarConselho(request);

        // Listando todos os conselhos
        List<ConselhoResponseDTO> response = conselhoService.listarConselhos();

        // Verificando se a lista não está vazia e se o ID do primeiro conselho é válido
        assertThat(response).isNotEmpty();
        assertThat(response.get(0).id()).isNotNull();
    }

    @Test
    void deveBuscarConselhoPorId() {
        // Adicionando os parâmetros obrigatórios
        LocalDate dataInicio = LocalDate.now();  // Hoje
        LocalDate dataFim = LocalDate.now().plusMonths(1); // Um mês a partir de hoje
        String etapas = "Etapa 1";  // Exemplo de etapa

        // Criando o DTO com todos os parâmetros
        ConselhoRequestDTO request = new ConselhoRequestDTO(
                turma.getId(),
                dataInicio,    // dataInicio
                dataFim,       // dataFim
                aluno1.getId(),
                aluno2.getId(),
                pedagogico.getId(),
                etapas          // etapas
        );

        // Criando o conselho
        ConselhoResponseDTO createdConselho = conselhoService.criarConselho(request);

        // Buscando pelo ID
        ConselhoResponseDTO response = conselhoService.buscarConselhoPorId(createdConselho.id());

        // Verificando se a resposta está correta
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(createdConselho.id());
    }


    @Test
    void deveAtualizarConselhoComSucesso() {
        // Adicionando os parâmetros obrigatórios
        LocalDate dataInicio = LocalDate.now();  // Hoje
        LocalDate dataFim = LocalDate.now().plusMonths(1); // Um mês a partir de hoje
        String etapas = "Etapa 1";  // Exemplo de etapa

        // Criando o DTO original com todos os parâmetros
        ConselhoRequestDTO request = new ConselhoRequestDTO(
                turma.getId(),
                dataInicio,    // dataInicio
                dataFim,       // dataFim
                aluno1.getId(),
                aluno2.getId(),
                pedagogico.getId(),
                etapas          // etapas
        );

        // Criando o conselho
        ConselhoResponseDTO createdConselho = conselhoService.criarConselho(request);

        // Alterando os parâmetros
        LocalDate updatedDataInicio = LocalDate.now().plusDays(1);  // Alterando a data de início
        LocalDate updatedDataFim = LocalDate.now().plusMonths(2);  // Alterando a data de fim
        String updatedEtapas = "Etapa 2";  // Alterando a etapa

        // Criando o DTO atualizado
        ConselhoRequestDTO updatedRequest = new ConselhoRequestDTO(
                turma.getId(),
                updatedDataInicio,  // Novo dataInicio
                updatedDataFim,     // Novo dataFim
                aluno2.getId(),
                aluno1.getId(),
                pedagogico.getId(),
                updatedEtapas       // Nova etapa
        );

        // Atualizando o conselho
        ConselhoResponseDTO updatedConselho = conselhoService.atualizarConselho(createdConselho.id(), updatedRequest);

        // Verificando se as alterações foram aplicadas
        assertThat(updatedConselho).isNotNull();
        assertThat(updatedConselho.idRepresentante1()).isEqualTo(aluno2.getId());  // Novo representante 1
        assertThat(updatedConselho.idRepresentante2()).isEqualTo(aluno1.getId());  // Novo representante 2
    }


    @Test
    void deveDeletarConselhoComSucesso() {
        // Adicionando os parâmetros obrigatórios
        LocalDate dataInicio = LocalDate.now();  // Hoje
        LocalDate dataFim = LocalDate.now().plusMonths(1); // Um mês a partir de hoje
        String etapas = "Etapa 1";  // Exemplo de etapa

        // Criando o DTO com todos os parâmetros
        ConselhoRequestDTO request = new ConselhoRequestDTO(
                turma.getId(),
                dataInicio,    // dataInicio
                dataFim,       // dataFim
                aluno1.getId(),
                aluno2.getId(),
                pedagogico.getId(),
                etapas          // etapas
        );

        // Criando o conselho
        ConselhoResponseDTO createdConselho = conselhoService.criarConselho(request);

        // Deletando o conselho
        conselhoService.deletarConselho(createdConselho.id());

        // Verificando se o conselho foi deletado
        assertThat(conselhoRepository.findById(createdConselho.id())).isEmpty();
    }

}
