package com.conselho.api.service.entity;

import com.conselho.api.dto.mapper.entity.SupervisorMapper;
import com.conselho.api.exception.pedagogico.PedagogicoNaoExiste;
import com.conselho.api.dto.request.SupervisorRequestDTO;
import com.conselho.api.dto.response.entity.SupervisorResponseDTO;
import com.conselho.api.model.entity.Supervisor;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.model.usuario.UsuarioRole;
import com.conselho.api.repository.entity.SupervisorRepository;
import com.conselho.api.repository.entity.UsuarioRepository;
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

    public List<SupervisorResponseDTO> listarSupervisores() {
        return usuarioRepository.findByRole(UsuarioRole.SUPERVISOR)
                .stream()
                .map(usuario -> new SupervisorResponseDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail()
                ))
                .toList();
    }

    public SupervisorResponseDTO buscarSupervisorPorId(Long id) {
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

    public SupervisorResponseDTO atualizarSupervisor(Long id, SupervisorRequestDTO request) {
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

       Supervisor salvo = repository.save(supervisor);
       return mapper.paraResposta(salvo);
    }

    public void deletarSupervisor(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new PedagogicoNaoExiste();
        }
        usuarioRepository.deleteById(id);
    }
}
