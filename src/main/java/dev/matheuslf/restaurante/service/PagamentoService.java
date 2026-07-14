package dev.matheuslf.restaurante.service;

import org.springframework.stereotype.Service;

import dev.matheuslf.restaurante.cliente.PagamentoClient;
import dev.matheuslf.restaurante.domain.entity.FechamentoConta;
import dev.matheuslf.restaurante.domain.entity.Mesa;
import dev.matheuslf.restaurante.domain.entity.Pagamento;
import dev.matheuslf.restaurante.domain.entity.Pedido;
import dev.matheuslf.restaurante.domain.enums.FormaPagamento;
import dev.matheuslf.restaurante.domain.enums.StatusMesa;
import dev.matheuslf.restaurante.domain.enums.StatusPagamento;
import dev.matheuslf.restaurante.domain.enums.StatusPedido;
import dev.matheuslf.restaurante.dto.PagamentoRequest;
import dev.matheuslf.restaurante.dto.PagamentoResponse;
import dev.matheuslf.restaurante.exception.RegraNegocioException;
import dev.matheuslf.restaurante.repository.FechamentoContaRepository;
import dev.matheuslf.restaurante.repository.MesaRepository;
import dev.matheuslf.restaurante.repository.PagamentoRepository;
import dev.matheuslf.restaurante.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagamentoService {
    private final PagamentoClient pagamentoClient;
    private final FechamentoContaRepository fechamentoContaRepository;
    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;
    private final PagamentoRepository pagamentoRepository;

    @Transactional
    public void pagar(Long pedidoId, String formaPagamento) {
        FechamentoConta fechamento = fechamentoContaRepository.findByPedidoId(pedidoId).orElseThrow(() -> new RegraNegocioException("Conta não encontrada!"));
        PagamentoResponse response = pagamentoClient.processar(
            new PagamentoRequest(fechamento.getTotal().doubleValue(), formaPagamento)
        );
        if ("APROVADO".equals(response.status())) {
            Pedido pedido = fechamento.getPedido();
            pedido.setStatus(StatusPedido.FECHADO);
            Mesa mesa = pedido.getMesa();
            mesa.setStatus(StatusMesa.LIVRE);
            Pagamento pagamento = new Pagamento();
            pagamento.setFormaPagamento(FormaPagamento.valueOf(formaPagamento));
            pagamento.setStatus(StatusPagamento.APROVADO);
            pagamento.setValor(fechamento.getTotal());
            pagamento.setDataPagamento(fechamento.getDataFechamento());

            pedidoRepository.save(pedido);
            mesaRepository.save(mesa);
            pagamentoRepository.save(pagamento);
        }
    }
}
