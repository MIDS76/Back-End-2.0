package com.conselho.api.repository.entity;

import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.model.usuario.UsuarioRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByEmail(String email);
    List<Usuario> findByRole(UsuarioRole role);
    List<Usuario> findByRoleAndAtivo(UsuarioRole usuarioRole, boolean atividade);
    List<Usuario> findByAtivo(boolean atividade);

}
