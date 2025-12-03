package com.conselho.api.repository.entity;

import com.conselho.api.model.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor,Long> {

    UserDetails findByEmail(String email);

    UserDetails findByNome(String nome);

}
