package dev.matheuslf.restaurante.cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import dev.matheuslf.restaurante.dto.PagamentoRequest;
import dev.matheuslf.restaurante.dto.PagamentoResponse;

@FeignClient(name = "pagament-client", url = "${pagamento.api.url}")
public interface PagamentoClient {
    @PostMapping("/pagamentos/processar")
    PagamentoResponse processar(@RequestBody PagamentoRequest request);
}
