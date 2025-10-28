package com.conselho.api.dto.request;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record AlunoRequest(
        @NotBlank(message = "O nome do aluno é obrigatório!")
        @NotNull(message = "O nome deve ser informado.")
        String nome,
        @NotBlank(message = "Email está inválido!")
        @NotNull(message = "O email deve ser informado.")
        String email,
        @NotBlank(message = "Email está inválido!")
        @NotNull(message = "A senha deve ser informado.")
        String senha,
        @NotBlank(message = "Representante está inválido!")
        @NotNull(message = "Deve ser informado se é representante.")
        @AssertFalse
        boolean is_representative
){
}
