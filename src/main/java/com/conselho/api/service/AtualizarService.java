package com.conselho.api.service;

import com.conselho.api.model.entity.Aluno;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.repository.entity.AlunoRepository;
import com.conselho.api.repository.entity.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class AtualizarService {

    private UsuarioRepository repository;
    private AlunoRepository alunoRepository;

    public void atualizarSenha(Long id, Map<String, String> camposAtualizacao) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        if (camposAtualizacao.containsKey("senha")) {
            String novaSenha = camposAtualizacao.get("senha");
            String senhaCriptografada = new BCryptPasswordEncoder().encode(novaSenha);
            usuario.setSenha(senhaCriptografada);

            if (usuario.isPrimeiroAcesso()) {
                usuario.setPrimeiroAcesso(false);
            }

            repository.save(usuario);
        } else {
            throw new RuntimeException("Campo de senha não fornecido!");
        }
    }

    public void atualizarRepresentante(Long id, Map<String, Boolean> camposAtualizacao) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado!"));

        if (camposAtualizacao.containsKey("representante")) {
            boolean novoRepresentante = camposAtualizacao.get("representante");
            aluno.setRepresentante(novoRepresentante);
            alunoRepository.save(aluno);
        } else {
            throw new RuntimeException("Campo de representante não fornecido!");
        }
    }

    public void atualizarAtividade(Long id, Map<String, Boolean> camposAtualizacao) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado!"));

        if(camposAtualizacao.containsKey("ativo")) {
            boolean novoAtivo = camposAtualizacao.get("ativo");
            usuario.setAtivo(novoAtivo);
            repository.save(usuario);
        }else{
            throw new RuntimeException("Campo de ativo não fornecido!");
        }
    }
}
