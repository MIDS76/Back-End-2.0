package com.conselho.api.exception.notificacao;

public class NotificacaoNaoExisteException extends RuntimeException {
    public NotificacaoNaoExisteException (){
        super("Notificação não encontrado.");
    }
}
