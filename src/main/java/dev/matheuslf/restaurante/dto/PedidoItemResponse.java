package dev.matheuslf.restaurante.dto;

import java.math.BigDecimal;

import dev.matheuslf.restaurante.domain.entity.PedidoItem;
import dev.matheuslf.restaurante.domain.enums.StatusItemPedido;

public record PedidoItemResponse(
    Long id,
    Long pedidoId,
    Long produtoId,
    String produtoNome,
    Integer quantidade,
    BigDecimal precoUnitario,
    BigDecimal total,
    String observacao,
    StatusItemPedido status
) {
    public static PedidoItemResponse fromEntity(PedidoItem pedidoItem) {
        BigDecimal total = pedidoItem.getPrecoUnitario().multiply(new BigDecimal(pedidoItem.getQuantidade()));
        return new PedidoItemResponse(
            pedidoItem.getId(),
            pedidoItem.getPedido().getId(),
            pedidoItem.getProduto().getId(),
            pedidoItem.getProduto().getNome(),
            pedidoItem.getQuantidade(),
            pedidoItem.getPrecoUnitario(),
            total,
            pedidoItem.getObservacao(),
            pedidoItem.getStatus()
        );
    }
}
