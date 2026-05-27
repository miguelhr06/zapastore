package com.lasonadora.ecomerce.repository;

import com.lasonadora.ecomerce.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
