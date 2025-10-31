package com.conselho.api.service;

import com.conselho.api.dto.mapper.AlunoMapper;
import com.conselho.api.dto.request.AlunoRequest;
import com.conselho.api.dto.response.AlunoResponse;
import com.conselho.api.exception.aluno.AlunoNaoExisteException;
import com.conselho.api.exception.aluno.AlunoJaExisteException;
import com.conselho.api.model.Aluno;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.model.usuario.UsuarioRole;
import com.conselho.api.repository.AlunoRepository;
import com.conselho.api.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AlunoService {

    private final AlunoRepository repository;
    private final UsuarioRepository usuarioRepository;

    private final AlunoMapper mapper;

    public List<AlunoResponse> buscarAlunos() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream()
                .filter(u -> UsuarioRole.ALUNO.equals(u.getRole()))
                .map(u -> {
                    if (u instanceof Aluno aluno) {
                        return new AlunoResponse(
                                aluno.getId(),
                                aluno.getNome(),
                                aluno.getEmail(),
                                aluno.getSenha(),
                                aluno.isRepresentante()
                        );
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public AlunoResponse buscarAlunoPorId(Long idAluno) {
        Optional<Usuario> usuario = usuarioRepository.findById(idAluno);
        if(usuario == null){
            throw new RuntimeException("Aluno não encontrado!");
        }

        Usuario newUsuario = usuario.get();

        if(newUsuario.getRole() != UsuarioRole.ALUNO){
            throw new RuntimeException("O Usuario não é um aluno");
        }

       return mapper.paraResposta((Aluno) newUsuario);
    }

    public void atualizarAluno(Long idAluno, AlunoRequest request) {
        Aluno aluno = repository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        if (aluno.getRole() != UsuarioRole.ALUNO) {
            throw new RuntimeException("O usuário não é um aluno");
        }

        if (request.email() != null && !request.email().equals(aluno.getEmail())) {
            var existing = usuarioRepository.findByEmail(request.email());
            if (existing != null && ((Usuario) existing).getId() != null
                    && !((Usuario) existing).getId().equals(idAluno)) {
                throw new RuntimeException("Email já cadastrado por outro usuário");
            }
        }

        mapper.paraUpdate(request, aluno);

        Aluno salvo = repository.save(aluno);
    }

    public AlunoResponse deletarAluno(Long idAluno) {
        Aluno aluno = (Aluno) usuarioRepository.findById(idAluno).orElseThrow(() ->
                new AlunoNaoExisteException());

        repository.deleteById(idAluno);

        return mapper.paraResposta(aluno);
    }

    public boolean isRepresentante(Long idAluno) {
        return repository.existsByIdAndRepresentanteTrue(idAluno);
    }

    public Aluno getRepresentante() {
        return repository.findByRepresentanteTrue();
    }
}
