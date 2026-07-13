package dev.matheuslf.restaurante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.matheuslf.restaurante.domain.entity.PedidoItem;
import dev.matheuslf.restaurante.domain.enums.StatusItemPedido;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {
    List<PedidoItem> findByPedidoId(Long id);
    List<PedidoItem> findByStatusOrderByIdAsc(StatusItemPedido status);
    List<PedidoItem> findByPedidoIdAndStatusNot(Long pedidoId, StatusItemPedido status);
    @Query(
        """
        SELECT i
        FROM PedidoItem i
        JOIN FETCH i.produto
        JOIN FETCH i.pedido p
        JOIN FETCH p.mesa
        WHERE i.status = :status
        ORDER BY i.id        
        """
    )
    List<PedidoItem> buscarItensComProdutoEPedido(StatusItemPedido status);
}
