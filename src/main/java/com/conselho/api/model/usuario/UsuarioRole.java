package com.conselho.api.model.usuario;

public enum UsuarioRole {

    PEDAGOGICO,
    SUPERVISOR,
    ALUNO,
    PROFESSOR;

    public String getRoleName() {
        return this.name();
    }
}
