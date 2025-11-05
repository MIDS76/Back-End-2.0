package com.conselho.api.model.usuario;

public enum UsuarioRole {

    PEDAGOGICO,
    SUPERVISOR,
    ALUNO,
    PROFESSOR,
    WEG;

    public String getRoleName() {
        return this.name();
    }
}
