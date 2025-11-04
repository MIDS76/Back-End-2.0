package com.conselho.api.dto.mapper;

import com.conselho.api.dto.request.SupervisorRequestDTO;
import com.conselho.api.dto.response.SupervisorResponse;
import com.conselho.api.model.Supervisor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component

public class SupervisorMapper {

    public Supervisor paraEntidade(SupervisorRequestDTO supervisorRequestDTO){
        return new Supervisor(supervisorRequestDTO.nome(), supervisorRequestDTO.email(), supervisorRequestDTO.senha());
    }

    public SupervisorResponse paraResposta(Supervisor supervisor){
        return new SupervisorResponse(supervisor.getId(), supervisor.getNome(), supervisor.getEmail());
    }

    public Supervisor paraUpdate(SupervisorRequestDTO supervisorRequestDTO, Supervisor supervisor) {
        if (supervisorRequestDTO.nome() != null && !Objects.equals(supervisorRequestDTO.nome(), supervisor.getNome())) {
            supervisor.setNome(supervisorRequestDTO.nome());
        }
        if (supervisorRequestDTO.email() != null && !Objects.equals(supervisorRequestDTO.email(), supervisor.getEmail())) {
            supervisor.setEmail(supervisorRequestDTO.email());
        }
        if (supervisorRequestDTO.senha() != null && !Objects.equals(supervisorRequestDTO.senha(), supervisor.getSenha())) {
            supervisor.setSenha(supervisorRequestDTO.senha());
        }
        return supervisor;
    }
}