package com.lasonadora.ecomerce.controller;

import com.lasonadora.ecomerce.model.Producto;
import com.lasonadora.ecomerce.service.ProductoService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/productos")
    public String listarProductos(HttpSession session, Model model) {

        // 1️⃣ Validar sesión
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }

        // 2️⃣ Obtener productos
        List<Producto> productos = productoService.obtenerTodos();
        model.addAttribute("productos", productos);

        // 3️⃣ Mostrar página
        return "productos";
    }
}
