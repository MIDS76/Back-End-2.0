package com.conselho.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record PedagogicoRequestDTO(

        @NotBlank(message = "O nome do pedagógico é obrigatório.")
        @NotNull(message = "O nome deve ser informado.")
        String nome,

        @NotBlank(message = "O email do pedagógico é obrigatório")
        @NotNull(message = "O email deve ser informado.")
        @Email
        String email,

        @NotBlank(message = "A senha do pedagógico é obrigatório")
        @NotNull(message = "A senha deve ser informado.")
        String senha
) {
}