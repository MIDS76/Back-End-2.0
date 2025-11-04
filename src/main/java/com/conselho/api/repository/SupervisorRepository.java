package com.conselho.api.repository;

import com.conselho.api.model.Professor;
import com.conselho.api.model.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor,Long> {

    boolean existsByNome(String nome);
    UserDetails findByEmail(String email);
}
