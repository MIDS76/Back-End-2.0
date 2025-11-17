package com.conselho.api.service;

import com.conselho.api.dto.mapper.entity.UsuarioMapper;
import com.conselho.api.dto.request.*;
import com.conselho.api.dto.request.entity.AlunoRequestDTO;
import com.conselho.api.dto.request.entity.PedagogicoRequestDTO;
import com.conselho.api.dto.request.entity.ProfessorRequestDTO;
import com.conselho.api.dto.request.entity.WegRequestDTO;
import com.conselho.api.dto.response.entity.UsuarioResponseDTO;
import com.conselho.api.model.entity.*;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.repository.entity.*;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CadastroService {

    private UsuarioMapper mapper;
    private UsuarioRepository usuarioRepository;
    private AlunoRepository alunoRepository;
    private ProfessorRepository professorRepository;
    private PedagogicoRepository pedagogicoRepository;
    private SupervisorRepository supervisorRepository;
    private WegRepository wegRepository;

    public String criptografarSenha(String senha){
        return new BCryptPasswordEncoder().encode(senha);
    }

    public UsuarioResponseDTO cadastrarAluno(AlunoRequestDTO request){

        String emailGerado;
        if (request.email().contains("@")) {
            emailGerado = request.email();
        } else {
            emailGerado = request.nome().toLowerCase().replaceAll("\\s+", "") + "@estudante.sesisenai.org.br";
        }

        if(alunoRepository.findByEmail(request.email()) != null){
            throw new RuntimeException("Email já cadastrado!");
        }

        if(alunoRepository.findByNome(request.nome()) != null){
            throw new RuntimeException("Nome já cadastrado");
        }

        String senhaCriptografada = criptografarSenha(request.matricula());
        Aluno aluno = new Aluno(
                request.nome(),
                emailGerado,
                senhaCriptografada,
                request.matricula(),
                false
        );
        Usuario salvo = usuarioRepository.save(aluno);
        alunoRepository.save(aluno);
        return mapper.paraResposta(salvo);
    }

    public UsuarioResponseDTO cadastroPedagogico(PedagogicoRequestDTO request) {

        if(pedagogicoRepository.findByNome(request.nome()) != null){
            throw new RuntimeException("Nome já cadastrado");
        }

        String senhaCriptografada = criptografarSenha("primeiroAcesso");
        Pedagogico pedagogico = new Pedagogico(request.nome(), request.email(), senhaCriptografada);
        Usuario salvo = usuarioRepository.save(pedagogico);
        pedagogicoRepository.save(pedagogico);
        return mapper.paraResposta(salvo);
    }

    public UsuarioResponseDTO cadastroProfessor(ProfessorRequestDTO request){

        if(professorRepository.findByEmail(request.email()) != null){
            throw new RuntimeException("Email já cadastrado!");
        }

        if(professorRepository.findByNome(request.nome()) != null){
            throw new RuntimeException("Nome já cadastrado");
        }

        String senhaCriptografada = criptografarSenha("primeiroAcesso");
        Professor professor = new Professor(request.nome(), request.email(), senhaCriptografada);
        Usuario salvo = usuarioRepository.save(professor);
        professorRepository.save(professor);
        return mapper.paraResposta(salvo);
    }

    public UsuarioResponseDTO cadastroSupervisor(SupervisorRequestDTO request){

        if(supervisorRepository.findByEmail(request.email()) != null){
            throw new RuntimeException("Email já cadastrado!");
        }

        if(supervisorRepository.findByNome(request.nome()) != null){
            throw new RuntimeException("Nome já cadastrado");
        }

        String senhaCriptografada = criptografarSenha("primeiroAcesso");
        Supervisor supervisor = new Supervisor(request.nome(), request.email(), senhaCriptografada);
        Usuario salvo =  usuarioRepository.save(supervisor);
        supervisorRepository.save(supervisor);
        return mapper.paraResposta(salvo);
    }

    public UsuarioResponseDTO cadastroWeg(WegRequestDTO request){

        if(wegRepository.findByEmail(request.email()) != null){
            throw new RuntimeException("Email já cadastrado!");
        }

        if(wegRepository.findByNome(request.nome()) != null){
            throw new RuntimeException("Nome já cadastrado");
        }

        String senhaCriptografada = criptografarSenha("primeiroAcesso");
        Weg weg = new Weg(request.nome(), request.email(), senhaCriptografada);
        Usuario salvo = usuarioRepository.save(weg);
        wegRepository.save(weg);

        return mapper.paraResposta(salvo);
    }

    public void cadastroAdmin(WegRequestDTO requestDTO){
        String senhaCliptografada = criptografarSenha("admin123");
        Admin admin = new Admin(requestDTO.nome(), requestDTO.email(), senhaCliptografada);
        usuarioRepository.save(admin);
    }
}
