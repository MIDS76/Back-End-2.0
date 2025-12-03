package com.conselho.api.model.conselho;

import java.lang.reflect.Array;
import java.util.Arrays;

public enum EtapasConselho {
    NAO_INICIADO,
    PRE_CONSELHO,
    CONSELHO,
    AGUARDANDO_RESULTADO,
    RESULTADO;

    public static boolean existeEtapa(String nome){
        return Arrays.stream(values())
                .anyMatch(e -> e.name().equalsIgnoreCase(nome));
    }
}
