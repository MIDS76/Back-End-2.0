package com.conselho.api.repository.entity;

import com.conselho.api.model.entity.Weg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface WegRepository extends JpaRepository<Weg, Long> {
    UserDetails findByEmail(String email);

    UserDetails findByNome(String nome);
}
