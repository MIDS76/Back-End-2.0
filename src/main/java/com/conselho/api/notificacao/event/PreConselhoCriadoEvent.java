package com.conselho.api.notificacao.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PreConselhoCriadoEvent {
    private final Long preConselhoId;
    private final Long conselhoId;
    private final Long idRepresentante1;
    private final Long idRepresentante2;
}