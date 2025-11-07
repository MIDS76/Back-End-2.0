package com.conselho.api.repository;

import com.conselho.api.model.Pedagogico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface PedagogicoRepository extends JpaRepository<Pedagogico, Long> {

    boolean existsByNome(String nome);
    UserDetails findByEmail(String email);
}
