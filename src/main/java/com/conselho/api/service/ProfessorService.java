package com.conselho.api.service;

import com.conselho.api.dto.mapper.ProfessorMapper;
import com.conselho.api.dto.response.PedagogicoResponse;
import com.conselho.api.exception.pedagogico.PedagogicoNaoExiste;
import com.conselho.api.exception.professor.ProfessorExisteException;
import com.conselho.api.exception.professor.ProfessorNaoExisteException;
import com.conselho.api.dto.request.ProfessorRequest;
import com.conselho.api.dto.response.ProfessorResponse;
import com.conselho.api.model.Pedagogico;
import com.conselho.api.model.Professor;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.model.usuario.UsuarioRole;
import com.conselho.api.repository.ProfessorRepository;
import com.conselho.api.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProfessorService {

    private final ProfessorRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final ProfessorMapper mapper;


    public List<ProfessorResponse> listarProfessores() {
        return usuarioRepository.findByRole(UsuarioRole.PROFESSOR)
                .stream()
                .map(usuario -> new ProfessorResponse(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail()
                ))
                .toList();
    }

    public ProfessorResponse buscarProfessorPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario == null) {
            throw new RuntimeException("Professor não encontrado!");
        }

        Usuario newUsuario = usuario.get();

        if (newUsuario.getRole() != UsuarioRole.PROFESSOR) {
            throw new RuntimeException("O Usuario não é um professor");
        }

        return mapper.paraResposta((Professor) newUsuario);
    }

    public void atualizarProfessor(Long id, ProfessorRequest request) {
        Professor professor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        if (professor.getRole() != UsuarioRole.PROFESSOR) {
            throw new RuntimeException("O usuário não é um Professor");
        }

        if (request.email() != null && !request.email().equals(professor.getEmail())) {
            var existing = usuarioRepository.findByEmail(request.email());
            if (existing != null && ((Usuario) existing).getId() != null
                    && !((Usuario) existing).getId().equals(id)) {
                throw new RuntimeException("Email já cadastrado por outro usuário");
            }
        }
        mapper.paraUpdate(request, professor);

        repository.save(professor);
    }

    public void deletarProfessor(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new PedagogicoNaoExiste();
        }
        usuarioRepository.deleteById(id);
    }
}
