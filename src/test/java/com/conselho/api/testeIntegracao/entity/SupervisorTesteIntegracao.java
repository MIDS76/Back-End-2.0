package com.conselho.api.testeIntegracao.entity;


import com.conselho.api.dto.request.SupervisorRequestDTO;
import com.conselho.api.dto.response.entity.SupervisorResponseDTO;
import com.conselho.api.model.entity.Supervisor;
import com.conselho.api.model.usuario.UsuarioRole;
import com.conselho.api.repository.entity.SupervisorRepository;
import com.conselho.api.service.entity.SupervisorService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class SupervisorTesteIntegracao {

    private final SupervisorService supervisorService;
    private final SupervisorRepository supervisorRepository;
    public SupervisorTesteIntegracao(
            SupervisorService supervisorService,
            SupervisorRepository supervisorRepository
    ) {
        this.supervisorService = supervisorService;
        this.supervisorRepository = supervisorRepository;
    }
    private Supervisor supervisor;

    @BeforeEach
    void setup() {
        supervisor = new Supervisor();
        supervisor.setNome("Juci");
        supervisor.setEmail("jucii@gmail.com");
        supervisor.setSenha("primeiroAcesso");
        supervisor.setRole(UsuarioRole.SUPERVISOR);

        supervisorRepository.save(supervisor);
    }

    @Test
    void deveListarTodosSupervisoresComSucesso() {
        var supervisao = supervisorService.listarSupervisores();

        assertThat(supervisao).isNotEmpty();
    }

    @Test
    void deveBuscarSupervisorPorIdComSucesso() {
        SupervisorResponseDTO response = supervisorService.buscarSupervisorPorId(supervisor.getId());

        assertThat(response).isNotNull();
        assertThat(response.nome()).isEqualTo("Juci");
        assertThat(response.email()).isEqualTo("jucii@gmail.com");
    }

    @Test
    void deveAtualizarSupervisorComSucesso() {
        SupervisorRequestDTO request = new SupervisorRequestDTO("Michelle", "michelle@gmail.com");

        supervisorService.atualizarSupervisor(supervisor.getId(), request);

        Supervisor updatedSupervisor = supervisorRepository.findById(supervisor.getId()).orElseThrow();
        assertThat(updatedSupervisor.getNome()).isEqualTo("Michelle");
        assertThat(updatedSupervisor.getEmail()).isEqualTo("michelle@gmail.com");
    }

    @Test
    void deveDeletarSupervisorComSucesso() {
        Long id = supervisor.getId();
        supervisorService.deletarSupervisor(id);

        assertThat(supervisorRepository.findById(id)).isEmpty();
    }

    @Test
    void naoDeveAtualizarSupervisorComEmailDuplicado() {
        Supervisor supervisor2 = new Supervisor();
        supervisor2.setNome("Andre");
        supervisor2.setEmail("dede@gmail.com");
        supervisor2.setSenha("dede123");
        supervisor2.setRole(UsuarioRole.SUPERVISOR);
        supervisorRepository.save(supervisor2);

        SupervisorRequestDTO request = new SupervisorRequestDTO("Fabiano", "dede@gmail.com");

        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () ->
                supervisorService.atualizarSupervisor(supervisor.getId(), request)
        );
    }
}
