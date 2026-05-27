package com.lasonadora.ecomerce.service.impl;

import com.lasonadora.ecomerce.model.DetallePedido;
import com.lasonadora.ecomerce.model.Pedido;
import com.lasonadora.ecomerce.repository.DetallePedidoRepository;
import com.lasonadora.ecomerce.repository.PedidoRepository;
import com.lasonadora.ecomerce.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository; // ✅ Este guarda el pedido principal

    @Autowired
    private DetallePedidoRepository detalleRepository; // ✅ Este guarda los detalles del pedido

    @Override
    public Pedido guardarPedido(Pedido pedido) {
        double total = 0;

        // Calculamos subtotales y el total general
        for (DetallePedido detalle : pedido.getDetalles()) {
            detalle.setSubtotal(detalle.getPrecio() * detalle.getCantidad());
            detalle.setPedido(pedido);
            total += detalle.getSubtotal();
        }

        pedido.setTotal(total);
        pedidoRepository.save(pedido); // Guardamos el pedido principal
        detalleRepository.saveAll(pedido.getDetalles()); // Guardamos los detalles

        return pedido;
    }
}
