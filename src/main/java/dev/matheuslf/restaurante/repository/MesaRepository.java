package dev.matheuslf.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.matheuslf.restaurante.domain.entity.Mesa;

public interface MesaRepository extends JpaRepository<Mesa, Long> {

}
