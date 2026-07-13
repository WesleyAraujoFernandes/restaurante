package dev.matheuslf.restaurante.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.matheuslf.restaurante.domain.entity.FechamentoConta;

public interface FechamentoContaRepository extends JpaRepository<FechamentoConta, Long> {
    boolean existsByPedidoId(Long id);
    Optional<FechamentoConta> findByPedidoId(Long id);
}
