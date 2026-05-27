package com.lasonadora.ecomerce.service.impl;

import com.lasonadora.ecomerce.model.CarritoItem;
import com.lasonadora.ecomerce.model.Producto;
import com.lasonadora.ecomerce.service.CarritoService; // 👈 este import debe existir
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarritoServiceImpl implements CarritoService {

    private final List<CarritoItem> items = new ArrayList<>();

    @Override
    public void agregarProducto(Producto producto, int cantidad) {
        for (CarritoItem item : items) {
            if (item.getProducto().getId().equals(producto.getId())) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        items.add(new CarritoItem(producto, cantidad));
    }

    @Override
    public List<CarritoItem> obtenerItems() {
        return items;
    }

    @Override
    public void vaciar() {
        items.clear();
    }
}
