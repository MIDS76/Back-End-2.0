package com.conselho.api.dto.response.entity;

import org.springframework.validation.annotation.Validated;

@Validated
public record SupervisorResponseDTO(

        Long id,
        String nome,
        String email
) {
}
