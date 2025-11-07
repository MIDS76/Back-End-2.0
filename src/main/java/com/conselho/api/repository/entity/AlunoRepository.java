package com.conselho.api.repository.entity;

import com.conselho.api.model.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    boolean existsByNome(String nome);
    boolean existsById(Long idAluno);
    Aluno findByRepresentanteTrue();
    boolean existsByIdAndRepresentanteTrue(Long id);

    UserDetails findByEmail(String email);

}
