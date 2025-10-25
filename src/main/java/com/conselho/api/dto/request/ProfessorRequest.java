package com.conselho.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record ProfessorRequest (

        @NotBlank(message = "O nome do professor é obrigatório")
        @NotNull(message = "O nome do deve ser informado!")
         String nome,

        @NotBlank(message = "O email do professor é obrigatório")
        @NotNull(message = "O email do deve ser informado!")
        String email,

        @NotBlank(message = "A senha do professor é obrigatória")
        @NotNull(message = "A senha do deve ser informada!")
        String senha
){
}






