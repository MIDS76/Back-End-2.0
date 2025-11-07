package com.conselho.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record SupervisorRequestDTO(
        @NotBlank(message = "O nome do supervisor é obrigatório")
        @NotNull(message = "O nome do deve ser informado!")
        String nome,

        @NotBlank(message = "O email do supervisor é obrigatório")
        @NotNull(message = "O email do deve ser informado!")
        String email

) {
}
