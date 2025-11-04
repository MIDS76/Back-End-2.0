package com.conselho.api.service;

import com.conselho.api.dto.request.AlunoRequestDTO;
import com.conselho.api.dto.request.PedagogicoRequestDTO;
import com.conselho.api.dto.request.ProfessorRequestDTO;
import com.conselho.api.dto.request.SupervisorRequestDTO;
import com.conselho.api.model.Aluno;
import com.conselho.api.model.Pedagogico;
import com.conselho.api.model.Professor;
import com.conselho.api.model.Supervisor;
import com.conselho.api.model.usuario.UsuarioRole;
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

    public void cadastrarAluno(AlunoRequestDTO request){
        if(alunoRepository.findByEmail(request.email()) != null){
            throw new RuntimeException("Email já cadastrado!");
        }

        String senhaCriptografada = criptografarSenha(request.senha());
        Aluno newAluno = new Aluno(request.nome(), request.email(), senhaCriptografada, request.representante());
        usuarioRepository.save(newAluno);
        alunoRepository.save(newAluno);
    }

    public void cadastroPedagogico(PedagogicoRequestDTO request) {
        if(pedagogicoRepository.findByEmail(request.email()) != null){
            throw new RuntimeException("Email já cadastrado!");
        }

        String senhaCriptografada = criptografarSenha(request.senha());
        Pedagogico pedagogico = new Pedagogico(request.nome(), request.email(), senhaCriptografada);
        usuarioRepository.save(pedagogico);
        pedagogicoRepository.save(pedagogico);
    }

    public void cadastroProfessor(ProfessorRequestDTO request){
        if(professorRepository.findByEmail(request.email()) != null){
            throw new RuntimeException("Email já cadastrado!");
        }

        String senhaCriptografada = criptografarSenha(request.senha());
        Professor professor = new Professor(request.nome(), request.email(), senhaCriptografada);
        usuarioRepository.save(professor);
        professorRepository.save(professor);
    }

    public void cadastroSupervisor(SupervisorRequestDTO request){
        if(supervisorRepository.findByEmail(request.email()) != null){
            throw new RuntimeException("Email já cadastrado!");
        }

        String senhaCriptografada = criptografarSenha(request.senha());
        Supervisor supervisor = new Supervisor(request.nome(), request.email(), senhaCriptografada);
        usuarioRepository.save(supervisor);
        supervisorRepository.save(supervisor);
    }
}
