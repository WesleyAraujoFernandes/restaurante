package dev.matheuslf.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.matheuslf.restaurante.domain.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
