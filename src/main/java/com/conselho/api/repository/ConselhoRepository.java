package com.conselho.api.repository;

import com.conselho.api.model.conselho.Conselho;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConselhoRepository extends JpaRepository<Conselho, Long> {
}
