package com.conselho.api.repository;

import com.conselho.api.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    boolean existsByNome(String nome);
    boolean existsById(Long idAluno);
    Aluno findByRepresentanteTrue();
    boolean existsByIdAndRepresentanteTrue(Long id);

    UserDetails findByEmail(String email);

}
