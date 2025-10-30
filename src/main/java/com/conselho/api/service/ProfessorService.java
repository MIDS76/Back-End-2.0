package com.conselho.api.service;

import com.conselho.api.dto.mapper.ProfessorMapper;
import com.conselho.api.exception.professor.ProfessorExisteException;
import com.conselho.api.exception.professor.ProfessorNaoExisteException;
import com.conselho.api.dto.request.ProfessorRequest;
import com.conselho.api.dto.response.ProfessorResponse;
import com.conselho.api.model.Professor;
import com.conselho.api.repository.ProfessorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProfessorService {

    private final ProfessorRepository repository;
    private final ProfessorMapper mapper;

    public ProfessorResponse criarProfessor(ProfessorRequest professorRequest){

        if(repository.existsByNome(professorRequest.nome())){
             new ProfessorExisteException();
        }
        return mapper.paraRespostaProfessor(repository.save(mapper.paraEntidadeProfessor(professorRequest)));
    }

    public List<ProfessorResponse> listarProfessores(){
        return repository.findAll().stream()
                .map(mapper::paraRespostaProfessor)
                .toList();
    }

    public ProfessorResponse buscarProfessorPorId(Long id){
        Professor professor = repository.findById(id).orElseThrow(()->
                new ProfessorNaoExisteException());

        return mapper.paraRespostaProfessor(professor);
    }

    public ProfessorResponse atualizarProfessor(Long id, ProfessorRequest professorRequest){
        Professor professor = repository.findById(id).orElseThrow(() ->
                new ProfessorNaoExisteException());

        Professor newProfessor = mapper.paraUpdateProfessor(professorRequest, professor);
        repository.save(newProfessor);
        return mapper.paraRespostaProfessor(newProfessor);
    }

    public ProfessorResponse deletarProfessor(Long id) {
        Professor professor = repository.findById(id).orElseThrow(() ->
                new ProfessorNaoExisteException());

        repository.deleteById(id);

        return mapper.paraRespostaProfessor(professor);
    }

}
