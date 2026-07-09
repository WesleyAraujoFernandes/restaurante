package dev.matheuslf.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.matheuslf.restaurante.domain.entity.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    
}
