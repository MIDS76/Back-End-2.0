package com.conselho.api.service.entity;

import com.conselho.api.dto.mapper.entity.UsuarioMapper;
import com.conselho.api.dto.response.entity.AlunoResponseDTO;
import com.conselho.api.dto.response.entity.UsuarioResponseDTO;
import com.conselho.api.model.entity.Aluno;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.model.usuario.UsuarioRole;
import com.conselho.api.repository.entity.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UsuarioService {

    private UsuarioRepository repository;
    private UsuarioMapper mapper;


    public List<UsuarioResponseDTO> listarUsuarios() {
        List<Usuario> usuarios = repository.findAll();
        List<UsuarioResponseDTO> responseDTO = usuarios.stream()
                .map(mapper::paraResposta)
                .collect(Collectors.toList());

        return responseDTO;
    }

    public UsuarioResponseDTO buscarUsuarioPorId(Long idUsuario) {
        Optional<Usuario> usuario = repository.findById(idUsuario);

        if (usuario == null) {
            throw new RuntimeException("Usuario não encontrado!");
        }

        Usuario newUsuario = usuario.get();

        return mapper.paraResposta((Usuario) newUsuario);
    }

    public List<UsuarioResponseDTO> buscarAtividade(boolean campoAtivo) {

        List<Usuario> usuarios = repository.findByAtivo(campoAtivo);

        return usuarios.stream()
                .map(mapper::paraResposta)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<UsuarioResponseDTO> ordenarUsuariosOrdemAlfabetica(String ordem) {
        Comparator<UsuarioResponseDTO> comparator = Comparator.comparing(usuario -> usuario.nome());
        List<UsuarioResponseDTO> usuarios = listarUsuarios();

        if ("Z-A".equalsIgnoreCase(ordem)) {
            usuarios.sort(comparator.reversed());
        } else {
            usuarios.sort(comparator);
        }

        return usuarios;
    }

    public List<UsuarioResponseDTO> bucarPorRole(String role) {
        List<UsuarioResponseDTO> usuarios = listarUsuarios();

        if(role != null && !role.isEmpty()){
           usuarios = usuarios.stream()
                    .filter(usuario -> usuario.role().toString().equalsIgnoreCase(role))
                   .collect(Collectors.toList());
        }

        if(usuarios.isEmpty()){
            throw new RuntimeException("Não foi encontrado nenhum usuario com esta role!");
        }
        return usuarios;
    }
}
