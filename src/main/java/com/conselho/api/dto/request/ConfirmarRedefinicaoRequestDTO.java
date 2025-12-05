package com.conselho.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConfirmarRedefinicaoRequestDTO(
        @Email(message = "Email inválido!")
        @NotBlank(message = "Esta campo é obrigatório!")
        String email,
        @NotBlank(message = "Esta campo é obrigatório!")
        @NotNull(message = "A nova senha deve ser informada!")
        String novaSenha
) {}