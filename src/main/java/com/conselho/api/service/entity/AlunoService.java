package com.conselho.api.service.entity;

import com.conselho.api.dto.mapper.entity.AlunoMapper;
import com.conselho.api.dto.request.entity.AlunoRequestDTO;
import com.conselho.api.dto.response.entity.AlunoResponseDTO;
import com.conselho.api.exception.aluno.AlunoNaoExisteException;
import com.conselho.api.model.entity.Aluno;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.model.usuario.UsuarioRole;
import com.conselho.api.repository.entity.AlunoRepository;
import com.conselho.api.repository.entity.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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

   public List<AlunoResponseDTO> listarAlunos() {
       List<Usuario> usuarios = usuarioRepository.findAll();

       return usuarios.stream()
               .filter(u -> UsuarioRole.ALUNO.equals(u.getRole()))
               .map(u -> {
                   if (u instanceof Aluno aluno) {
                       return new AlunoResponseDTO(
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

   public AlunoResponseDTO buscarAlunoPorId(Long idAluno) {
       Optional<Usuario> usuario = usuarioRepository.findById(idAluno);
       if (usuario.isEmpty()) {
           throw new RuntimeException("Aluno não encontrado!");
       }

       Usuario newUsuario = usuario.get();

       if (newUsuario.getRole() != UsuarioRole.ALUNO) {
           throw new RuntimeException("O Usuario não é um aluno");
       }

       return mapper.paraResposta((Aluno) newUsuario);
   }
    public AlunoResponseDTO atualizarAluno(Long idAluno, AlunoRequestDTO request) {
        Aluno aluno = repository.findById(idAluno)
                .orElseThrow(AlunoNaoExisteException::new);

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
        return mapper.paraResposta(salvo);
    }

    public void deletarAluno(Long idAluno) {
        repository.findById(idAluno)
                .orElseThrow(AlunoNaoExisteException::new);

        repository.deleteById(idAluno);
    }

}