package com.conselho.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record SupervisorRequest(
        @NotBlank(message = "O nome do supervisor é obrigatório")
        @NotNull(message = "O nome do deve ser informado!")
        String nome,

        @NotBlank(message = "O email do supervisor é obrigatório")
        @NotNull(message = "O email do deve ser informado!")
        String email,

        @NotBlank(message = "A senha do supervisor é obrigatória")
        @NotNull(message = "A senha do supervidor deve ser informada!")
        String senha
) {
}
