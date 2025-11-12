package com.conselho.api.model.usuario;

public enum UsuarioRole {

    PEDAGOGICO,
    SUPERVISOR,
    ALUNO,
    PROFESSOR,
    WEG,
    ADMIN;

    public String getRoleName() {
        return this.name();
    }
}
