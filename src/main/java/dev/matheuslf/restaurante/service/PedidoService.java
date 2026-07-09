package dev.matheuslf.restaurante.service;

import org.springframework.stereotype.Service;

import dev.matheuslf.restaurante.dto.PedidoResponse;
import dev.matheuslf.restaurante.repository.MesaRepository;
import dev.matheuslf.restaurante.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;

    public PedidoResponse abrirPedido() {
        
    }
}
