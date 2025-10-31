package com.conselho.api.dto.mapper;

import com.conselho.api.dto.request.SupervisorRequest;
import com.conselho.api.dto.response.SupervisorResponse;
import com.conselho.api.model.Supervisor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component

public class SupervisorMapper {

    public Supervisor paraEntidade(SupervisorRequest supervisorRequest){
        return new Supervisor(supervisorRequest.nome(), supervisorRequest.email(), supervisorRequest.senha(), supervisorRequest.role());
    }

    public SupervisorResponse paraResposta(Supervisor supervisor){
        return new SupervisorResponse(supervisor.getId(), supervisor.getNome(), supervisor.getEmail());
    }

    public Supervisor paraUpdate(SupervisorRequest supervisorRequest, Supervisor supervisor) {
        if (supervisorRequest.nome() != null && !Objects.equals(supervisorRequest.nome(), supervisor.getNome())) {
            supervisor.setNome(supervisorRequest.nome());
        }
        if (supervisorRequest.email() != null && !Objects.equals(supervisorRequest.email(), supervisor.getEmail())) {
            supervisor.setEmail(supervisorRequest.email());
        }
        if (supervisorRequest.senha() != null && !Objects.equals(supervisorRequest.senha(), supervisor.getSenha())) {
            supervisor.setSenha(supervisorRequest.senha());
        }
        return supervisor;
    }
}