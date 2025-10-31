package com.conselho.api.service;

import com.conselho.api.dto.mapper.SupervisorMapper;
import com.conselho.api.dto.response.ProfessorResponse;
import com.conselho.api.exception.pedagogico.PedagogicoNaoExiste;
import com.conselho.api.exception.supervisor.SupervisorExisteException;
import com.conselho.api.exception.supervisor.SupervisorNaoExisteException;
import com.conselho.api.dto.request.SupervisorRequest;
import com.conselho.api.dto.response.SupervisorResponse;
import com.conselho.api.model.Professor;
import com.conselho.api.model.Supervisor;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.model.usuario.UsuarioRole;
import com.conselho.api.repository.SupervisorRepository;
import com.conselho.api.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SupervisorService {

    private final SupervisorRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final SupervisorMapper mapper;

    public List<SupervisorResponse> listarSupervisores() {
        return usuarioRepository.findByRole(UsuarioRole.SUPERVISOR)
                .stream()
                .map(usuario -> new SupervisorResponse(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail()
                ))
                .toList();
    }

    public SupervisorResponse buscarSupervisorPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario == null) {
            throw new RuntimeException("Supervisor não encontrado!");
        }

        Usuario newUsuario = usuario.get();

        if (newUsuario.getRole() != UsuarioRole.SUPERVISOR) {
            throw new RuntimeException("O Usuario não é um supervisor");
        }

        return mapper.paraResposta((Supervisor) newUsuario);
    }

    public void atualizarSupervisor(Long id, SupervisorRequest request) {
        Supervisor supervisor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supervisor não encontrado"));

        if (supervisor.getRole() != UsuarioRole.SUPERVISOR) {
            throw new RuntimeException("O usuário não é um Supervisor");
        }

        if (request.email() != null && !request.email().equals(supervisor.getEmail())) {
            var existing = usuarioRepository.findByEmail(request.email());
            if (existing != null && ((Usuario) existing).getId() != null
                    && !((Usuario) existing).getId().equals(id)) {
                throw new RuntimeException("Email já cadastrado por outro usuário");
            }
        }
        mapper.paraUpdate(request, supervisor);

        repository.save(supervisor);

    }

    public void deletarSupervisor(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new PedagogicoNaoExiste();
        }
        usuarioRepository.deleteById(id);
    }
}
