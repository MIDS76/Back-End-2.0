package com.conselho.api.service;

import com.conselho.api.dto.request.AlunoRequest;
import com.conselho.api.dto.response.UsuarioResponse;
import com.conselho.api.model.Aluno;
import com.conselho.api.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CadastroService {

    private UsuarioRepository usuarioRepository;
    private AlunoRepository alunoRepository;
    private ProfessorRepository professorRepository;
    private PedagogicoRepository pedagogicoRepository;
    private SupervisorRepository supervisorRepository;

    public void cadastrarAluno(AlunoRequest request){
        if(alunoRepository.findByEmail(request.email()) != null){
            throw new RuntimeException("Email já cadastrado!");
        }

        String senhaCriptografada = new BCryptPasswordEncoder().encode(request.senha());
        String role = request.role();
        Aluno newAluno = new Aluno(request.nome(), request.email(), senhaCriptografada, role, false);
        alunoRepository.save(newAluno);
        usuarioRepository.save(newAluno);
    }
}
