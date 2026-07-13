package dev.matheuslf.restaurante.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import dev.matheuslf.restaurante.domain.entity.FechamentoConta;
import dev.matheuslf.restaurante.domain.entity.Pedido;
import dev.matheuslf.restaurante.domain.entity.PedidoItem;
import dev.matheuslf.restaurante.domain.enums.StatusItemPedido;
import dev.matheuslf.restaurante.domain.enums.StatusPedido;
import dev.matheuslf.restaurante.dto.FechamentoContaRequest;
import dev.matheuslf.restaurante.dto.FechamentoContaResponse;
import dev.matheuslf.restaurante.exception.RegraNegocioException;
import dev.matheuslf.restaurante.repository.FechamentoContaRepository;
import dev.matheuslf.restaurante.repository.PedidoItemRepository;
import dev.matheuslf.restaurante.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FechamentoContaService {
    private final PedidoRepository pedidoRepository;
    private final PedidoItemRepository pedidoItemRepository;
    private final FechamentoContaRepository fechamentoContaRepository;

    public FechamentoContaResponse fecharConta(Long pedidoId, FechamentoContaRequest request) {
        Pedido pedido = buscarPedidoPorId(pedidoId);
        if (pedido.getStatus() == StatusPedido.FECHADO) {
            throw new RegraNegocioException("Pedido já está fechado!");
        }
        if (pedido.getStatus() == StatusPedido.CANCELADO) {
            throw new RegraNegocioException("Pedido foi fechado. Não pode ser cancelado!");   
        }
        if (fechamentoContaRepository.existsByPedidoId(pedidoId)) {
            throw new RegraNegocioException("Pedido já foi fechado!");
        }
        List<PedidoItem> itens = pedidoItemRepository.findByPedidoId(pedidoId);
        if (itens.isEmpty()) {
            throw new RegraNegocioException("Não é possível fechar uma conta sem itens! Cancele o pedido ou adicione itens.");
        }
        List<PedidoItem> itensNaoEntregues = pedidoItemRepository.findByPedidoIdAndStatusNot(pedidoId, StatusItemPedido.ENTREGUE);
        if (!itensNaoEntregues.isEmpty()) {
            throw new RegraNegocioException("Todos os itens devem estar entregues para fechar a conta!");
        }

        BigDecimal subtotal = itens.stream().map(item -> item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade()))).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal taxaServico = request.taxaServico() != null ? request.taxaServico() : BigDecimal.ZERO;
        BigDecimal desconto = request.desconto() != null ? request.desconto() : BigDecimal.ZERO;

        if (taxaServico.compareTo(BigDecimal.ZERO) < 0) {
            throw new RegraNegocioException("Taxa de servico não pode ser negativa!");
        }

        if (desconto.compareTo(BigDecimal.ZERO) < 0) {
            throw new RegraNegocioException("Desconto não pode ser negativo!");
        }

        BigDecimal total = subtotal.add(taxaServico.subtract(desconto));
        if (total.compareTo(BigDecimal.ZERO) < 0) {
            throw new RegraNegocioException("Total da conta não pode ser negativo!");
        }

        FechamentoConta fechamento = new FechamentoConta();
        fechamento.setPedido(pedido);
        fechamento.setSubtotal(subtotal);
        fechamento.setTaxaServico(taxaServico);
        fechamento.setDesconto(desconto);
        fechamento.setTotal(total);
        
        pedido.setStatus(StatusPedido.FECHADO);
        pedido.setDataFechamento(LocalDateTime.now());

        FechamentoConta fechamentoSalvo = fechamentoContaRepository.save(fechamento);
        pedidoRepository.save(pedido);

        return FechamentoContaResponse.fromEntity(fechamentoSalvo);
    }

    public FechamentoContaResponse buscarPorPedido(Long pedidoId) {
        FechamentoConta fechamento = fechamentoContaRepository.findByPedidoId(pedidoId)
            .orElseThrow(() -> new RegraNegocioException("Fechamento não encontrado para o pedido solicitado!"));
        return FechamentoContaResponse.fromEntity(fechamento);
    }

    private Pedido buscarPedidoPorId(Long pedidoId) {
        return pedidoRepository.findById(pedidoId).orElseThrow(() -> new RegraNegocioException("Pedido não encontrado!"));
    }
}
