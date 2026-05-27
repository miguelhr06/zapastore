package com.lasonadora.ecomerce.service;
import com.lasonadora.ecomerce.service.CarritoService;

import com.lasonadora.ecomerce.model.CarritoItem;
import com.lasonadora.ecomerce.model.Producto;

import java.util.List;

public interface CarritoService {

    void agregarProducto(Producto producto, int cantidad);

    List<CarritoItem> obtenerItems();

    void vaciar();
}
