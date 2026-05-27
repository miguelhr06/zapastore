package com.lasonadora.ecomerce.repository;

import com.lasonadora.ecomerce.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VentaRepository extends JpaRepository<Venta, Integer> {

    Optional<Venta> findTopByOrderByIdDesc();

    List<Venta> findByCorreo(String correo);
}
