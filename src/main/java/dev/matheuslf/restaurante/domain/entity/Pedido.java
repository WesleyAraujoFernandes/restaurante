package dev.matheuslf.restaurante.domain.entity;

import java.time.LocalDateTime;

import dev.matheuslf.restaurante.domain.enums.StatusPedido;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "pedidos")
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "data_abertura")
    private LocalDateTime dataAbertura;
    @Column(name = "data_fechamento")
    private LocalDateTime dataFechamento;
    @Column(name = "status")
    private StatusPedido status = StatusPedido.ABERTO;
    private String observacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mesa_id")
    private Mesa mesa;

    @PrePersist
    public void prePersist() {
        this.dataAbertura = LocalDateTime.now();
    }
}
