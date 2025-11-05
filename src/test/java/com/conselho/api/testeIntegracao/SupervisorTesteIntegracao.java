package com.conselho.api.testeIntegracao;
import com.conselho.api.dto.request.SupervisorRequestDTO;
import com.conselho.api.dto.response.SupervisorResponse;
import com.conselho.api.model.Supervisor;
import com.conselho.api.model.usuario.UsuarioRole;
import com.conselho.api.repository.SupervisorRepository;
import com.conselho.api.repository.UsuarioRepository;
import com.conselho.api.service.SupervisorService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class SupervisorTesteIntegracao {
    @Autowired
    private SupervisorService supervisorService;

    @Autowired
    private SupervisorRepository supervisorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Supervisor supervisor;

    @BeforeEach
    void setup() {
        supervisor = new Supervisor();
        supervisor.setNome("Juci");
        supervisor.setEmail("jucii@gmail.com");
        supervisor.setSenha("juju123");
        supervisor.setRole(UsuarioRole.SUPERVISOR);
        supervisorRepository.save(supervisor);
    }

    @Test
    void deveListarTodosSupervisoresComSucesso() {
        List<SupervisorResponse> supervisores = supervisorService.listarSupervisores();

        assertThat(supervisores).isNotEmpty();
        assertThat(supervisores.get(0).nome()).isEqualTo("Juci");
    }

    @Test
    void deveBuscarSupervisorPorIdComSucesso() {
        SupervisorResponse response = supervisorService.buscarSupervisorPorId(supervisor.getId());

        assertThat(response).isNotNull();
        assertThat(response.nome()).isEqualTo("Juci");
        assertThat(response.email()).isEqualTo("jucii@gmail.com");
    }

    @Test
    void deveAtualizarSupervisorComSucesso() {
        SupervisorRequestDTO request = new SupervisorRequestDTO("Michelle", "michelle@test.com", "mimi123");

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

        SupervisorRequestDTO request = new SupervisorRequestDTO("Fabiano", "dede@gmail.com", "432");

        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () ->
                supervisorService.atualizarSupervisor(supervisor.getId(), request)
        );
    }
}
