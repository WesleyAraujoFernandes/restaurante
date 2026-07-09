package dev.matheuslf.restaurante.dto;

import java.time.LocalDateTime;

import dev.matheuslf.restaurante.domain.entity.Pedido;
import dev.matheuslf.restaurante.domain.enums.StatusPedido;

public record PedidoResponse(
        Long id,
        Long mesaId,
        Integer numeroMesa,
        LocalDateTime dataAbertura,
        LocalDateTime dataFechamento,
        StatusPedido status,
        String observacao) {
    public static PedidoResponse fromEntity(Pedido pedido) {
        return new PedidoResponse(
            pedido.getId(), 
            pedido.getMesa() != null ? pedido.getMesa().getId() : null, 
            pedido.getMesa() != null ? pedido.getMesa().getNumero() : null,
            pedido.getDataAbertura(), 
            pedido.getDataFechamento(), 
            pedido.getStatus(), 
            pedido.getObservacao());
    }
}