package com.lasonadora.ecomerce.controller;

import com.lasonadora.ecomerce.model.Pedido;
import com.lasonadora.ecomerce.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/carrito")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    private Pedido ultimoPedido; // para mostrar en reporte

    @GetMapping("/reporte")
    public String mostrarReporte(Model model) {
        model.addAttribute("pedido", ultimoPedido);
        return "reporte";
    }



}
