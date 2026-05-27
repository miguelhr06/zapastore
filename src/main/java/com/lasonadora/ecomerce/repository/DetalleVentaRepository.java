package com.lasonadora.ecomerce.repository;

import com.lasonadora.ecomerce.model.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
    List<DetalleVenta> findByVenta_Id(Long idVenta);
}
