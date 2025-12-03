package com.conselho.api.exception.conselho;

import com.conselho.api.dto.request.AtualizarEtapaRequestDTO;

public class EtapaInvalidaException extends RuntimeException {
    public EtapaInvalidaException(AtualizarEtapaRequestDTO etapa) {
        super("Etapa informada é inválida: " + etapa);
    }
}
