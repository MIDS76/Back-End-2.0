package com.conselho.api.repository;

import com.conselho.api.model.Weg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface WegRepository extends JpaRepository<Weg, Long> {
    UserDetails findByEmail(String email);
}
