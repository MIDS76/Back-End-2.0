package com.conselho.api.dto.mapper;

import com.conselho.api.dto.request.SupervisorRequestDTO;
import com.conselho.api.dto.response.SupervisorResponseDTO;
import com.conselho.api.model.Supervisor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component

public class SupervisorMapper {

    public Supervisor paraEntidade(SupervisorRequestDTO supervisorRequestDTO){
        return new Supervisor(supervisorRequestDTO.nome(), supervisorRequestDTO.email(), supervisorRequestDTO.senha());
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
        if (supervisorRequestDTO.senha() != null && !Objects.equals(supervisorRequestDTO.senha(), supervisor.getSenha())) {
            String senhaCriptografada = new BCryptPasswordEncoder().encode(supervisorRequestDTO.senha());
            supervisor.setSenha(senhaCriptografada);
            supervisor.setSenha(supervisorRequestDTO.senha());
        }
        return supervisor;
    }
}