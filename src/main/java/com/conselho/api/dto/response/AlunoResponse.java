package com.conselho.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public record AlunoResponse(

        Long id,
        String nome,
        String email,
        String senha,
        boolean representante


) {
}
