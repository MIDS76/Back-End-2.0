package com.conselho.api.dto.request.entity;

import com.conselho.api.model.usuario.UsuarioRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record AlunoRequestDTO(

        @NotBlank(message = "A matricula é obrigatória!")
        @NotNull(message = "A matricula deve ser informada.")
        String matricula,

        @NotBlank(message = "O nome do aluno é obrigatório!")
        @NotNull(message = "O nome deve ser informado.")
        String nome,

        @Email
        @NotBlank(message = "Email está inválido!")
        @NotNull(message = "O email deve ser informado.")
        String email
){
}
