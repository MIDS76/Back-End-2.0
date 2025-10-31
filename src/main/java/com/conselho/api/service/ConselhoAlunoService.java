package com.conselho.api.service;

import com.conselho.api.dto.mapper.AlunoMapper;
import com.conselho.api.dto.mapper.ConselhoAlunoMapper;
import com.conselho.api.dto.mapper.ConselhoMapper;
import com.conselho.api.dto.request.ConselhoAlunoRequest;
import com.conselho.api.dto.response.*;
import com.conselho.api.exception.ConselhoAluno.ConselhoAlunoNaoExiste;
import com.conselho.api.exception.aluno.AlunoNaoExisteException;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.model.ConselhoAluno;
import com.conselho.api.repository.AlunoRepository;
import com.conselho.api.repository.ConselhoAlunoRepository;
import com.conselho.api.repository.ConselhoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ConselhoAlunoService {

    @Autowired
    private ConselhoAlunoRepository conselhoAlunosRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ConselhoRepository conselhoRepository;

    private ConselhoAlunoMapper conselhoAlunoMapper;

    private AlunoMapper alunoMapper;
    private ConselhoMapper conselhoMapper;

    public List<AlunoResponse> getAlunosPorConselho(Long idConselho) {
        return conselhoAlunosRepository.findByConselhoId(idConselho)
                .stream()
                .map(rel -> alunoMapper.paraResposta(rel.getAluno()))
                .toList();
    }

    public List<ConselhoResponse> getConselhosPorAluno(Long idAluno) {
        return conselhoAlunosRepository.findByAlunoId(idAluno)
                .stream()
                .map(rel -> conselhoMapper.paraResposta(rel.getConselho()))
                .toList();
    }

    public List<ConselhoAlunoResponse> buscarTodos (){
        return conselhoAlunosRepository.findAll()
                .stream()
                .map(conselhoAlunoMapper::paraResposta)
                .toList();
    }

    public ConselhoAlunoResponse associarAlunoAConselho(ConselhoAlunoRequest request) {
        ConselhoAluno conselhoAluno = conselhoAlunoMapper.paraEntidade(request);

        conselhoAluno.setAluno(alunoRepository.findById(request.idAluno()).orElseThrow(AlunoNaoExisteException::new));
        conselhoAluno.setConselho(conselhoRepository.findById(request.idConselho()).orElseThrow(ConselhoNaoExiste::new));


        ConselhoAluno conselhoAlunosSalvo = conselhoAlunosRepository.save(conselhoAluno);
        return conselhoAlunoMapper.paraResposta(conselhoAlunosSalvo);
    }

    public void deletarConselhoAluno(Long id){
        if (!conselhoAlunosRepository.existsById(id)){
            throw new ConselhoAlunoNaoExiste();
        }
        conselhoAlunosRepository.deleteById(id);
    }

}

