package com.conselho.api.repository.entity;

import com.conselho.api.model.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    UserDetails findByEmail(String email);
}
