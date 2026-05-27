package com.lasonadora.ecomerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index"; // Vista: templates/index.html
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "contacto"; // Esto busca contacto.html en templates/
    }

    @GetMapping("/nosotros")
    public String nosotros() {
        return "nosotros"; // Esto busca contacto.html en templates/
    }

    @GetMapping("/ofertas")
    public String ofertas() {
        return "ofertas"; // la nueva página
    }



}
