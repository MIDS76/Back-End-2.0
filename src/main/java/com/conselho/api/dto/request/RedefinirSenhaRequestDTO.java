package com.conselho.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RedefinirSenhaRequestDTO(
        @Email(message = "Email inválido!")
        @NotBlank(message = "Esta campo é obrigatório!")
        String email
) {
}
