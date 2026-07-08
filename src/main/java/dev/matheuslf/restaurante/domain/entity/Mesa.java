package dev.matheuslf.restaurante.domain.entity;

import dev.matheuslf.restaurante.domain.enums.StatusMesa;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mesa")
public class Mesa {
    @Id
    @GeneratedValue
    private Long id;
    private Integer numero;
    private String descricao;
    private Integer capacidade;
    @Enumerated(EnumType.STRING)
    private StatusMesa status = StatusMesa.LIVRE;
}
