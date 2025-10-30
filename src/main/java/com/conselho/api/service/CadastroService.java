package com.conselho.api.service;

import com.conselho.api.dto.request.AlunoRequest;
import com.conselho.api.dto.request.PedagogicoRequest;
import com.conselho.api.dto.request.ProfessorRequest;
import com.conselho.api.dto.request.SupervisorRequest;
import com.conselho.api.dto.response.UsuarioResponse;
import com.conselho.api.model.Aluno;
import com.conselho.api.model.Pedagogico;
import com.conselho.api.model.Professor;
import com.conselho.api.model.Supervisor;
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

    public String criptografarSenha(String senha){
        return new BCryptPasswordEncoder().encode(senha);
    }

    public void cadastrarAluno(AlunoRequest request){
        if(alunoRepository.findByEmail(request.email()) != null){
            throw new RuntimeException("Email já cadastrado!");
        }

        String senhaCriptografada = criptografarSenha(request.senha());
        String role = request.role();
        Aluno newAluno = new Aluno(request.nome(), request.email(), senhaCriptografada, role, request.representante());
        usuarioRepository.save(newAluno);
        alunoRepository.save(newAluno);
    }

    public void cadastroPedagogico(PedagogicoRequest request) {
        if(pedagogicoRepository.findByEmail(request.email()) != null){
            throw new RuntimeException("Email já cadastrado!");
        }

        String senhaCriptografada = criptografarSenha(request.senha());
        String role = request.role();
        Pedagogico pedagogico = new Pedagogico(request.nome(), request.email(), senhaCriptografada, role);
        usuarioRepository.save(pedagogico);
        pedagogicoRepository.save(pedagogico);
    }

    public void cadastroProfessor(ProfessorRequest request){
        if(professorRepository.findByEmail(request.email()) != null){
            throw new RuntimeException("Email já cadastrado!");
        }

        String senhaCriptografada = criptografarSenha(request.senha());
        String role = request.role();
        Professor professor = new Professor(request.nome(), request.email(), senhaCriptografada, role);
        usuarioRepository.save(professor);
        professorRepository.save(professor);
    }

    public void cadastroSupervisor(SupervisorRequest request){
        if(supervisorRepository.findByEmail(request.email()) != null){
            throw new RuntimeException("Email já cadastrado!");
        }

        String senhaCriptografada = criptografarSenha(request.senha());
        String role = request.role();
        Supervisor supervisor = new Supervisor(request.nome(), request.email(), senhaCriptografada, role);
        usuarioRepository.save(supervisor);
        supervisorRepository.save(supervisor);
    }
}
