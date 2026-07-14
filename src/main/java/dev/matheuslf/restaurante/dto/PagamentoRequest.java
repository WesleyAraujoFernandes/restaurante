package dev.matheuslf.restaurante.dto;

public record PagamentoRequest(
    Double valor,
    String formaPagamento
) {

}
