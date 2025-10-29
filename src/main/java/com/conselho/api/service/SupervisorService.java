package com.conselho.api.service;

import com.conselho.api.dto.mapper.SupervisorMapper;
import com.conselho.api.exception.supervisor.SupervisorExisteException;
import com.conselho.api.exception.supervisor.SupervisorNaoExisteException;
import com.conselho.api.dto.request.SupervisorRequest;
import com.conselho.api.dto.response.SupervisorResponse;
import com.conselho.api.model.Supervisor;
import com.conselho.api.repository.SupervisorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SupervisorService {

    private final SupervisorRepository repository;
    private final SupervisorMapper mapper;

    public SupervisorResponse criarSupervisor(SupervisorRequest supervisorRequest){

        if(repository.existsByNome(supervisorRequest.nome())){
            new SupervisorExisteException();
        }

        return mapper.paraRespostaSupervisor(repository.save(mapper.paraEntidadeSupervisor(supervisorRequest)));
    }

    public List<SupervisorResponse> listarSupervisores(){
        return repository.findAll().stream()
                .map(mapper::paraRespostaSupervisor)
                .toList();
    }

    public SupervisorResponse buscarSupervisorPorId(Long id){
        Supervisor supervisor = repository.findById(id).orElseThrow(()->
                new SupervisorNaoExisteException());

        return mapper.paraRespostaSupervisor(supervisor);
    }

    public SupervisorResponse atualizarSupervisor(Long id, SupervisorRequest supervisorRequest){
        Supervisor supervisor = repository.findById(id).orElseThrow(() ->
                new SupervisorNaoExisteException());

        Supervisor newSupervisor = mapper.paraUpdateSupervisor(supervisorRequest, supervisor);
        repository.save(newSupervisor);
        return mapper.paraRespostaSupervisor(newSupervisor);
    }

    public SupervisorResponse deletarSupervisor(Long id) {
        Supervisor supervisor = repository.findById(id).orElseThrow(() ->
                new SupervisorNaoExisteException());

        repository.deleteById(id);

        return mapper.paraRespostaSupervisor(supervisor);
    }

}
