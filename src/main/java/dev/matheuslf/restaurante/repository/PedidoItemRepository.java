package dev.matheuslf.restaurante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.matheuslf.restaurante.domain.entity.PedidoItem;
import dev.matheuslf.restaurante.domain.enums.StatusItemPedido;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {
    List<PedidoItem> findByPedidoId(Long id);
    List<PedidoItem> findByStatusOrderByIdAsc(StatusItemPedido status);
}
