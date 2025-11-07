package com.conselho.api.service;

import com.conselho.api.dto.response.UsuarioResponseDTO;
import com.conselho.api.model.Aluno;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.repository.AlunoRepository;
import com.conselho.api.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class AtualizarService {

    private UsuarioRepository repository;
    private AlunoRepository alunoRepository;

    public void atualizarSenha(Long id,  Map<String, String> camposAtualizacao){
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        if (camposAtualizacao.containsKey("senha")) {
            String novaSenha = camposAtualizacao.get("senha");
            String senhaCriptografada = new BCryptPasswordEncoder().encode(novaSenha);
            usuario.setSenha(senhaCriptografada);
            repository.save(usuario);
        } else {
            throw new RuntimeException("Campo de senha não fornecido!");
        }
    }

    public void atualizarRepresentante(Long id, Map<String, Boolean> camposAtualizacao){
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado!"));

        if(camposAtualizacao.containsKey("representante")) {
            boolean novoRepresentante = camposAtualizacao.get("representante");
            aluno.setRepresentante(novoRepresentante);
            alunoRepository.save(aluno);
        }else{
            throw new RuntimeException("Campo de representante não fornecido!");
        }
    }
}
