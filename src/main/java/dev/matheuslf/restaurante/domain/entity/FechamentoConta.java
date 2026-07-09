package dev.matheuslf.restaurante.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "fechamento_conta")
@AllArgsConstructor
@NoArgsConstructor
public class FechamentoConta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal subtotal;
    @Column(name = "taxa_servico")
    private BigDecimal taxaServico;
    private BigDecimal desconto;
    private BigDecimal total;
    @Column(name = "data_fechamento")
    private LocalDateTime dataFechamento;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @PrePersist
    public void prePersist() {
        this.dataFechamento = LocalDateTime.now();
    }
}
