package com.conselho.api.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Valid
@Component
public record AtualizarEtapaRequestDTO(

        @NotBlank(message = "A nova etapa deve ser informada.")
        String novaEtapa,

        @NotNull(message = "A data de início do pré-conselho deve ser informada.")
        @FutureOrPresent(message = "A data de início deve ser hoje ou uma data futura.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate dataInicioPre,

        @NotNull(message = "A data de fim do pré-conselho deve ser informada.")
        @Future(message = "A data de fim deve ser uma data futura.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate dataFimPre

) { }
