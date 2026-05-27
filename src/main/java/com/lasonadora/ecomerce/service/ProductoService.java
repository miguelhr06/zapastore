package com.lasonadora.ecomerce.service;

import com.lasonadora.ecomerce.model.Producto;
import java.util.List;

public interface ProductoService {

    List<Producto> obtenerTodos();

    Producto obtenerPorId(Integer id);
}
