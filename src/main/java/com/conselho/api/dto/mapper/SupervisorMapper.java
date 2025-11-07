package com.conselho.api.dto.mapper;

import com.conselho.api.dto.request.SupervisorRequestDTO;
import com.conselho.api.dto.response.SupervisorResponseDTO;
import com.conselho.api.model.Supervisor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component

public class SupervisorMapper {

    public Supervisor paraEntidade(SupervisorRequestDTO supervisorRequestDTO, String senha){
        return new Supervisor(supervisorRequestDTO.nome(), supervisorRequestDTO.email(), senha);
    }

    public SupervisorResponseDTO paraResposta(Supervisor supervisor){
        return new SupervisorResponseDTO(supervisor.getId(), supervisor.getNome(), supervisor.getEmail());
    }

    public Supervisor paraUpdate(SupervisorRequestDTO supervisorRequestDTO, Supervisor supervisor) {
        if (supervisorRequestDTO.nome() != null && !Objects.equals(supervisorRequestDTO.nome(), supervisor.getNome())) {
            supervisor.setNome(supervisorRequestDTO.nome());
        }
        if (supervisorRequestDTO.email() != null && !Objects.equals(supervisorRequestDTO.email(), supervisor.getEmail())) {
            supervisor.setEmail(supervisorRequestDTO.email());
        }
        return supervisor;
    }
}