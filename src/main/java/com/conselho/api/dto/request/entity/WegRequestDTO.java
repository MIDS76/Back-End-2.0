package com.conselho.api.dto.request.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WegRequestDTO(
        @NotBlank(message = "O nome do usuario weg é obrigatório!")
        @NotNull(message = "O usuario weg deve ser informado.")
        String nome,

        @Email
        @NotBlank(message = "Email está inválido!")
        @NotNull(message = "O email deve ser informado.")
        String email
) {
}
