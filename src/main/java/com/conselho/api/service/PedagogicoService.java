package com.conselho.api.service;

import com.conselho.api.dto.mapper.PedagogicoMapper;
import com.conselho.api.dto.request.PedagogicoRequest;
import com.conselho.api.dto.response.PedagogicoResponse;
import com.conselho.api.exception.aluno.AlunoJaExisteException;
import com.conselho.api.exception.pedagogico.PedagogicoJaExiste;
import com.conselho.api.exception.pedagogico.PedagogicoNaoExiste;
import com.conselho.api.model.Aluno;
import com.conselho.api.model.Pedagogico;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.model.usuario.UsuarioRole;
import com.conselho.api.repository.PedagogicoRepository;
import com.conselho.api.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PedagogicoService {
    private final PedagogicoMapper mapper;
    private final PedagogicoRepository repository;
    private final UsuarioRepository usuarioRepository;


    // DELETE
    public void deletarPedagogico(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new PedagogicoNaoExiste();
        }
        usuarioRepository.deleteById(id);
    }

    // BUSCAR TODOS
    public List<PedagogicoResponse> buscarTodos() {
        return usuarioRepository.findByRole(UsuarioRole.PEDAGOGICO)
                .stream()
                .map(usuario -> new PedagogicoResponse(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail()
                ))
                .toList();
    }

    // BUSCAR POR ID
    public PedagogicoResponse buscarPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario == null) {
            throw new RuntimeException("Pedagogico não encontrado!");
        }

        Usuario newUsuario = usuario.get();

        if (newUsuario.getRole() != UsuarioRole.PEDAGOGICO) {
            throw new RuntimeException("O Usuario não é um pedagogico");
        }

        return mapper.paraResposta((Pedagogico) newUsuario);
    }

    // UPDATE
    public void update(Long id, PedagogicoRequest request) {
        Pedagogico pedagogico = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedagogico não encontrado"));

        if (pedagogico.getRole() != UsuarioRole.PEDAGOGICO) {
            throw new RuntimeException("O usuário não é um Pedagógico");
        }

        if (request.email() != null && !request.email().equals(pedagogico.getEmail())) {
            var existing = usuarioRepository.findByEmail(request.email());
            if (existing != null && ((Usuario) existing).getId() != null
                    && !((Usuario) existing).getId().equals(id)) {
                throw new RuntimeException("Email já cadastrado por outro usuário");
            }
        }

        mapper.paraUpdate(request, pedagogico);

        Pedagogico salvo = repository.save(pedagogico);
    }
}