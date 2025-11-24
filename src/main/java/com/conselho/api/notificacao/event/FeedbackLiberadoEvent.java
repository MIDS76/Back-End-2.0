package com.conselho.api.notificacao.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FeedbackLiberadoEvent {
    private final Long conselhoId;
    private final Long alunoId;
}