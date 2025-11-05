package com.conselho.api.serviceTesteUnitario;

import com.conselho.api.dto.mapper.SupervisorMapper;
import com.conselho.api.dto.request.SupervisorRequestDTO;
import com.conselho.api.dto.response.SupervisorResponseDTO;
import com.conselho.api.exception.pedagogico.PedagogicoNaoExiste;
import com.conselho.api.model.Supervisor;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.model.usuario.UsuarioRole;
import com.conselho.api.repository.SupervisorRepository;
import com.conselho.api.repository.UsuarioRepository;
import com.conselho.api.service.SupervisorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupervisorServiceTest {

    @InjectMocks
    private SupervisorService supervisorService;

    @Mock
    private SupervisorRepository supervisorRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private SupervisorMapper supervisorMapper;

    @Test
    void listarSupervisores_DeveRetornarListaDeSupervisores() {

        Usuario usuario1 = new Usuario(1L, "Jusci", "jusci@supervisor.com", "senha123", UsuarioRole.SUPERVISOR);
        Usuario usuario2 = new Usuario(2L, "Maria", "maria@supervisor.com", "senha123", UsuarioRole.SUPERVISOR);

        when(usuarioRepository.findByRole(UsuarioRole.SUPERVISOR)).thenReturn(List.of(usuario1, usuario2));

        List<SupervisorResponseDTO> result = supervisorService.listarSupervisores();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Jusci", result.get(0).nome());
        assertEquals("Maria", result.get(1).nome());
    }

    @Test
    void buscarSupervisorPorId_DeveLancarExcecao_QuandoSupervisorNaoExiste() {
        Long id = 1L;
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            supervisorService.buscarSupervisorPorId(id);
        });

        assertEquals("Supervisor não encontrado!", exception.getMessage());
    }

    @Test
    void buscarSupervisorPorId_DeveRetornarSupervisor_QuandoExiste() {
        Long id = 1L;
        Supervisor supervisor = new Supervisor("Maria", "maria@supervisor.com", "senha123");
        supervisor.setId(id);
        supervisor.setRole(UsuarioRole.SUPERVISOR);

        SupervisorResponseDTO response = new SupervisorResponseDTO(id, "Maria", "maria@supervisor.com");

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(supervisor));
        when(supervisorMapper.paraResposta(supervisor)).thenReturn(response);

        SupervisorResponseDTO result = supervisorService.buscarSupervisorPorId(id);

        assertNotNull(result);
        assertEquals("Maria", result.nome());
        assertEquals("maria@supervisor.com", result.email());
    }

    @Test
    void atualizarSupervisor_DeveAtualizarComSucesso() {
        Long id = 1L;
        SupervisorRequestDTO request = new SupervisorRequestDTO("Andrei", "andrei@supervisor.com", "senha123");
        Supervisor supervisorExistente = new Supervisor("andrei", "anderei@supervisor.com", "senha123");
        supervisorExistente.setId(id);
        supervisorExistente.setRole(UsuarioRole.SUPERVISOR);

        when(supervisorRepository.findById(id)).thenReturn(Optional.of(supervisorExistente));
        when(usuarioRepository.findByEmail(request.email())).thenReturn(null);

        Supervisor supervisorAtualizado = new Supervisor("Andrei", "andrei@supervisor.com", "senha123");
        when(supervisorRepository.save(supervisorExistente)).thenReturn(supervisorAtualizado);

        supervisorService.atualizarSupervisor(id, request);

        verify(supervisorRepository, times(1)).save(supervisorExistente);
    }

    @Test
    void deletarSupervisor_DeveLancarExcecao_QuandoNaoExiste() {
        Long id = 1L;

        when(usuarioRepository.existsById(id)).thenReturn(false);

        assertThrows(PedagogicoNaoExiste.class, () -> {
            supervisorService.deletarSupervisor(id);
        });

        verify(usuarioRepository, never()).deleteById(anyLong());
    }

    @Test
    void deletarSupervisor_DeveDeletarComSucesso() {
        Long id = 1L;

        when(usuarioRepository.existsById(id)).thenReturn(true);

        supervisorService.deletarSupervisor(id);

        verify(usuarioRepository, times(1)).deleteById(id);
    }
}
