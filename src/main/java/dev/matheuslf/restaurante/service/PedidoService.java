package dev.matheuslf.restaurante.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.matheuslf.restaurante.domain.entity.Mesa;
import dev.matheuslf.restaurante.domain.entity.Pedido;
import dev.matheuslf.restaurante.domain.entity.PedidoItem;
import dev.matheuslf.restaurante.domain.entity.Produto;
import dev.matheuslf.restaurante.domain.enums.StatusItemPedido;
import dev.matheuslf.restaurante.domain.enums.StatusMesa;
import dev.matheuslf.restaurante.domain.enums.StatusPedido;
import dev.matheuslf.restaurante.dto.PedidoItemRequest;
import dev.matheuslf.restaurante.dto.PedidoItemResponse;
import dev.matheuslf.restaurante.dto.PedidoRequest;
import dev.matheuslf.restaurante.dto.PedidoResponse;
import dev.matheuslf.restaurante.exception.RegraNegocioException;
import dev.matheuslf.restaurante.repository.MesaRepository;
import dev.matheuslf.restaurante.repository.PedidoItemRepository;
import dev.matheuslf.restaurante.repository.PedidoRepository;
import dev.matheuslf.restaurante.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoItemRepository pedidoItemRepository;

    public PedidoResponse abrirPedido(PedidoRequest pedidoRequest) {
        Mesa mesa = mesaRepository.findById(pedidoRequest.mesaId())
        .orElseThrow(() -> new RegraNegocioException("Mesa não encontrada!"));

        if (mesa.getStatus() != StatusMesa.LIVRE) {
            throw new RegraNegocioException("Mesa não está livre para a abertura de pedido!");
        }

        Pedido pedido = new Pedido();
        mesa.setStatus(StatusMesa.OCUPADA);
        pedido.setMesa(mesa);
        pedido.setStatus(StatusPedido.ABERTO);
        pedido.setObservacao(pedidoRequest.observacao());

        pedido = pedidoRepository.save(pedido);
        return PedidoResponse.fromEntity(pedido);
    }

    public Page<PedidoResponse> listar(Pageable pageable) {
        return pedidoRepository.findAll(pageable).map(PedidoResponse::fromEntity);
    }

    public PedidoResponse buscarPorId(Long id) {
        Pedido pedido = buscarPedidoPorId(id);
        return PedidoResponse.fromEntity(pedido);
    }

    public PedidoItemResponse adicionarItem(Long pedidoId, PedidoItemRequest request) {
        Pedido pedido = buscarPedidoPorId(pedidoId);
        if (pedido.getStatus() != StatusPedido.ABERTO) {
            throw new RegraNegocioException("Só é possível adicionar itens em pedidos abertos!");
        }

        Produto produto = produtoRepository.findById(request.produtoId()).orElseThrow(() -> new RegraNegocioException("Produto não encontrado!"));

        if (!produto.getDisponivel()) {
            throw new RegraNegocioException("Produto indisponivel!");
        }

        if (request.quantidade() == null || request.quantidade() <= 0) {
            throw new RegraNegocioException("A quantidade deve ser maior que zero!");
        }

        PedidoItem pedidoItem = new PedidoItem();
        pedidoItem.setPedido(pedido);
        pedidoItem.setProduto(produto);
        pedidoItem.setQuantidade(request.quantidade());
        pedidoItem.setPrecoUnitario(produto.getPreco());
        pedidoItem.setObservacao(request.observacao());
        pedidoItem.setStatus(StatusItemPedido.PENDENTE);

        pedidoItem = pedidoItemRepository.save(pedidoItem);
        return PedidoItemResponse.fromEntity(pedidoItem);
    }

    public List<PedidoItemResponse> listarItens(Long pedidoId) {
        buscarPedidoPorId(pedidoId);
        return pedidoItemRepository.findByPedidoId(pedidoId).stream().map(PedidoItemResponse::fromEntity).collect(Collectors.toList());
    }

    private Pedido buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id).orElseThrow(() -> new RegraNegocioException("Pedido nao encontrado!"));
    }
}
