package com.lasonadora.ecomerce.service.impl;

import com.lasonadora.ecomerce.model.Producto;
import com.lasonadora.ecomerce.repository.ProductoRepository;
import com.lasonadora.ecomerce.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto obtenerPorId(Integer id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no existe: " + id));
    }
}
