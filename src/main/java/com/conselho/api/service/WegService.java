package com.conselho.api.service;

import com.conselho.api.dto.mapper.WegMapper;
import com.conselho.api.dto.request.WegRequestDTO;
import com.conselho.api.dto.response.WegResponseDTO;
import com.conselho.api.exception.weg.WegNaoExisteException;
import com.conselho.api.model.Weg;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.model.usuario.UsuarioRole;
import com.conselho.api.repository.UsuarioRepository;
import com.conselho.api.repository.WegRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WegService {

    private final WegRepository repository;
    private final WegMapper mapper;
    private final UsuarioRepository usuarioRepository;

    // BUSCAR TODOS
    public List<WegResponseDTO> buscarTodos(){
        return usuarioRepository.findByRole(UsuarioRole.WEG)
                .stream()
                .map(usuario -> new WegResponseDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail()
                ))
                .toList();
    }

    // BUSCAR POR ID
    public WegResponseDTO buscarPorId(Long id){
        Usuario usuario = usuarioRepository.findById(id)
                    .orElseThrow(WegNaoExisteException::new);

        if (usuario.getRole() != UsuarioRole.WEG){
            throw new RuntimeException("O usuário não é um weg");
        }

        return mapper.paraResposta((Weg) usuario);
    }

    // ATUALIZAR
    public WegResponseDTO update (Long id, WegRequestDTO request){
        Weg weg = repository.findById(id)
                .orElseThrow(WegNaoExisteException::new);

        if (weg.getRole() != UsuarioRole.WEG){
            throw new RuntimeException("O usuário não é um weg");
        }

        if (request.email() != null && !request.email().equals(weg.getEmail())) {
            var existing = usuarioRepository.findByEmail(request.email());
            if (existing != null && ((Usuario) existing).getId() != null && !((Usuario) existing).getId().equals(id)) {
                throw new RuntimeException("Email já cadastrado por outro usuário");
            }
        }

        Weg atualizado = mapper.paraUpdate(request, weg);

        return mapper.paraResposta(repository.save(atualizado));
    }

    // DELETE
    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new WegNaoExisteException();
        }
        usuarioRepository.deleteById(id);
    }
}
