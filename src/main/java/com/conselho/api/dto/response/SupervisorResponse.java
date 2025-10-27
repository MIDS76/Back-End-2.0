package com.conselho.api.dto.response;

import org.springframework.validation.annotation.Validated;

@Validated
public record SupervisorResponse(

        Long id,
        String nome,
        String email,
        String senha
) {
}
