package dev.matheuslf.restaurante.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import dev.matheuslf.restaurante.dto.PedidoItemRequest;
import dev.matheuslf.restaurante.dto.PedidoItemResponse;
import dev.matheuslf.restaurante.dto.PedidoRequest;
import dev.matheuslf.restaurante.dto.PedidoResponse;
import dev.matheuslf.restaurante.service.PagamentoService;
import dev.matheuslf.restaurante.service.PedidoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    private final PedidoService pedidoService;
    private final PagamentoService pagamentoService; 

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse abrirPedido(@RequestBody PedidoRequest pedidoRequest) {
        return pedidoService.abrirPedido(pedidoRequest);
    }

    @GetMapping
    public Page<PedidoResponse> listar(Pageable pageable) {
        return pedidoService.listar(pageable);
    }

    @GetMapping("/{id}")
    public PedidoResponse buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id);
    }

    @PostMapping("/{pedidoId}/itens")
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoItemResponse adicionarItem(@PathVariable Long pedidoId, @RequestBody PedidoItemRequest request) {
        return pedidoService.adicionarItem(pedidoId, request);
    }

    @GetMapping("/{pedidoId}/itens")
    public List<PedidoItemResponse> listarItens(@PathVariable Long pedidoId) {
        return pedidoService.listarItens(pedidoId);
    }

    @PostMapping("/pedidos/{pedidoId}/pagar")
    @ResponseStatus(HttpStatus.OK)
    public void pagar(Long pedidoId, String formaPagamento) {
        pagamentoService.pagar(pedidoId, formaPagamento);
    }
}
