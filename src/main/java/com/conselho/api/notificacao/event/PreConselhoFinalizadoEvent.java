package com.conselho.api.notificacao.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PreConselhoFinalizadoEvent {
    private final Long preConselhoId;
    private final Long pedagogicoId;

}
