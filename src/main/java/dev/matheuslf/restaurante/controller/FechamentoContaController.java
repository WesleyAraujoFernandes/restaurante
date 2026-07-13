package dev.matheuslf.restaurante.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;

import dev.matheuslf.restaurante.dto.FechamentoContaRequest;
import dev.matheuslf.restaurante.dto.FechamentoContaResponse;
import dev.matheuslf.restaurante.service.FechamentoContaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pedidos/{pedidoId}/fechamento")
@RequiredArgsConstructor
public class FechamentoContaController {
    private final FechamentoContaService fechamentoContaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FechamentoContaResponse fecharConta(@PathVariable Long pedidoId, @RequestBody FechamentoContaRequest request) {
        return fechamentoContaService.fecharConta(pedidoId, request);
    }

    @GetMapping
    public FechamentoContaResponse buscarPorPedido(@PathVariable Long pedidoId) {
        return fechamentoContaService.buscarPorPedido(pedidoId);
    }
}
