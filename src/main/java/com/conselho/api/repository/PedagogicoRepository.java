package com.conselho.api.repository;

import com.conselho.api.model.Pedagogico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedagogicoRepository extends JpaRepository<Pedagogico, Long> {

    boolean existsByNome(String nome);
}
