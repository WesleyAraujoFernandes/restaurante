package dev.matheuslf.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.matheuslf.restaurante.domain.entity.PedidoItem;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {

}
