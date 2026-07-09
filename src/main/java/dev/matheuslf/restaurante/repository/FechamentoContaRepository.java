package dev.matheuslf.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.matheuslf.restaurante.domain.entity.FechamentoConta;

public interface FechamentoContaRepository extends JpaRepository<FechamentoConta, Long> {

}
