package dev.matheuslf.restaurante.domain.entity;

import java.math.BigDecimal;

import dev.matheuslf.restaurante.domain.enums.StatusItemPedido;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "pedidos_itens")
@AllArgsConstructor
@NoArgsConstructor
public class PedidoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantidade;
    @Column(name = "preco_unitario")
    private BigDecimal precoUnitario;
    private String observacao;
    @Enumerated(EnumType.STRING)
    private StatusItemPedido status = StatusItemPedido.PENDENTE;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto; 
}
