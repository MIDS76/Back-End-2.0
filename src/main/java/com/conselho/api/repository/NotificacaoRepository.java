package com.conselho.api.repository;

import com.conselho.api.model.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    List<Notificacao> findByUsuarioIdAndLidaFalseOrderByCriadaEmDesc(Long usuarioId);
    List<Notificacao> findByUsuarioIdOrderByCriadaEmDesc(Long usuarioId);
    long countByUsuarioIdAndLidaFalse (Long usuarioId);
}
