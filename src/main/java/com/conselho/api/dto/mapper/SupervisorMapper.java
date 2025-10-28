package com.conselho.api.dto.mapper;

import com.conselho.api.dto.request.SupervisorRequest;
import com.conselho.api.dto.response.SupervisorResponse;
import com.conselho.api.model.Supervisor;
import org.springframework.stereotype.Component;

@Component

public class SupervisorMapper {

    public Supervisor paraEntidadeSupervisor(SupervisorRequest supervisorRequest){
        return new Supervisor(supervisorRequest.nome(), supervisorRequest.email(), supervisorRequest.senha());
    }

    public SupervisorResponse paraRespostaSupervisor(Supervisor supervisor){
        return new SupervisorResponse(supervisor.getId(), supervisor.getNome(), supervisor.getEmail(), supervisor.getSenha());
    }

    public Supervisor paraUpdateSupervisor(SupervisorRequest supervisorRequest, Supervisor supervisor){
        if((supervisorRequest.nome() != supervisor.getNome() && supervisorRequest.nome() != null)){
            supervisor.setNome(supervisorRequest.nome());
        }
        if((supervisorRequest.email() != supervisor.getEmail() && supervisorRequest.email() != null)){
            supervisor.setEmail(supervisorRequest.email());
        }
        if((supervisorRequest.senha() != supervisor.getSenha() && supervisorRequest.senha() != null)){
            supervisor.setSenha(supervisorRequest.senha());
        }
        return supervisor;
    }
}