package com.lasonadora.ecomerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CarritoController {

    @GetMapping("/carrito")
    public String ocultarCarrito() {
        // Redirige al listado de productos para ocultar la página del carrito
        return "redirect:/productos";
    }


}
