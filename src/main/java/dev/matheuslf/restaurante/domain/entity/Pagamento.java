package dev.matheuslf.restaurante.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import dev.matheuslf.restaurante.domain.enums.FormaPagamento;
import dev.matheuslf.restaurante.domain.enums.StatusPagamento;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "pagamentos")
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal valor;
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;
    @Enumerated(EnumType.STRING)
    private StatusPagamento status = StatusPagamento.PENDENTE;
    @Column(name = "codigo_transacao_externa")
    private String codigoTransacaoExterna;
    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;
    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();
    }
}
